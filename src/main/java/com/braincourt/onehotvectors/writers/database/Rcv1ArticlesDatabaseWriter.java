package com.braincourt.onehotvectors.writers.database;

import com.braincourt.mysql.entities.RcvArticles;
import com.braincourt.mysql.entities.RcvArticlesWithTopicTags;
import com.braincourt.mysql.entities.RcvArticlesWithoutTopicTags;
import com.braincourt.mysql.repositories.RcvArticlesWithTopicTagsRepository;
import com.braincourt.mysql.repositories.RcvArticlesWithoutTopicTagsRepository;
import com.braincourt.onehotvectors.entitystreamers.Rcv1ArticleStreamer;
import com.braincourt.onehotvectors.writers.ReutersTagsToIdIndex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Component
public class Rcv1ArticlesDatabaseWriter extends DatabaseWriter<RcvArticles> {

    private final String indexPath;
    RcvArticlesWithoutTopicTagsRepository articlesWithoutTopicTagsRepository;
    RcvArticlesWithTopicTagsRepository articlesWithTopicTagsRepository;

    Rcv1ArticleStreamer rcv1ArticleStreamer;

    ReutersTagsToIdIndex reutersTagsToIdIndex;

    Random random = new Random(1);

    AtomicLong i = new AtomicLong();
    private AtomicInteger withTopicTagsWritten = new AtomicInteger();

    public Rcv1ArticlesDatabaseWriter(
            Rcv1ArticleStreamer rcv1ArticleStreamer,
            RcvArticlesWithoutTopicTagsRepository articlesWithoutTopicTagsRepository,
            RcvArticlesWithTopicTagsRepository articlesWithTopicTagsRepository,
            ReutersTagsToIdIndex reutersTagsToIdIndex,
            @Value("${processed.data.dir}") String processedDataDir) {

        this.articlesWithoutTopicTagsRepository = articlesWithoutTopicTagsRepository;
        this.articlesWithTopicTagsRepository = articlesWithTopicTagsRepository;
        this.reutersTagsToIdIndex = reutersTagsToIdIndex;
        this.rcv1ArticleStreamer = rcv1ArticleStreamer;
        this.indexPath = processedDataDir + "reutersIndex.ser";
    }

    @Override
    public void write() {
        LOG.info("Started writing to database");
        writeToDatabase();
        LOG.info(String.format("Finished writing %d elements to database", withTopicTagsWritten.get()));

        // create tags to articles hashmap
        LOG.info("Creating index");
        createIndex(); // TODO: Put in own processing step class
        LOG.info("Finished index");

        LOG.info("Started adding relevant documents to RCV1 dataset in DB...");
        addRelevantDocuments(); // TODO: Put in own processing step class
        LOG.info("Finished adding relevant documents to RCV1 dataset in DB");
    }

    private void createIndex() {
        try {
            //ObjectInputStream in = new ObjectInputStream(new FileInputStream("/Users/sahandzarrinkoub/School/year5/thesis/datasets/preprocessed_datasets/reutersIndex.ser"));
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(indexPath));
            reutersTagsToIdIndex = (ReutersTagsToIdIndex) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            LongStream.range(1, articlesWithTopicTagsRepository.count() + 1).boxed()
                    .map(id -> articlesWithTopicTagsRepository.findById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .peek(article -> {
                        if (article.getId() % 1000 == 0) {
                            LOG.info(String.format("Adding %dth article to index", article.getId()));
                        }
                    })
                    .forEach(this::addToIndex);

            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(indexPath));
                out.writeObject(reutersTagsToIdIndex);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

    }

    private void addToIndex(RcvArticles article) {
        Class clazz = article.getClass();
        Field[] fields = clazz.getFields();
        Arrays.stream(fields)
                .filter(field -> hasField(field, article))
                .filter(field -> !field.getName().equals("queryArticleNGramIndices"))
                .filter(field -> !field.getName().equals("queryArticleWordIndices"))
                .filter(field -> !field.getName().equals("articleId"))
                .filter(field -> !field.getName().equals("relevantId"))
                .filter(field -> !field.getName().equals("id"))
                .forEach(field -> addToIndex(field.getName(), article.getId()));
    }

    private void addToIndex(String fieldName, Long id) {
        if (!reutersTagsToIdIndex.containsKey(fieldName)) {
            Set<Long> postings = new HashSet<>();
            postings.add(id);
            reutersTagsToIdIndex.put(fieldName, postings);
        } else {
            Set<Long> postings = reutersTagsToIdIndex.get(fieldName);
            postings.add(id);
        }
    }

    private void writeToDatabase() {
        this.rcv1ArticleStreamer.getEntities().forEach(this::writeIfRoomLeft);
    }

    private void writeIfRoomLeft(RcvArticles entity) {
        if (withTopicTagsWritten.get() < 34303) {
            writeToDatabase(entity);
        }
    }

    private void writeToDatabase(RcvArticles rcvArticles) {
        if (rcvArticles instanceof RcvArticlesWithTopicTags) {
            articlesWithTopicTagsRepository.save((RcvArticlesWithTopicTags) rcvArticles);
            withTopicTagsWritten.getAndIncrement();
        } else {
            articlesWithoutTopicTagsRepository.save((RcvArticlesWithoutTopicTags) rcvArticles);
        }
        if (i.incrementAndGet() % 1000 == 0) {
            LOG.info(i.get() + " entities processed.");
        }
    }

    private boolean hasField(Field field, RcvArticles article) {
        try {
            Object fieldObj = field.get(article);
            Class fieldType = field.getType();
            if (fieldType.getName().equals("java.lang.String")) {
                String tags = (String) fieldObj;
                if (tags != null && !tags.isEmpty()) {
                    return true;
                }
            } else if (fieldType.getName().equals("boolean")) {
                boolean hasTag = (boolean) fieldObj;
                if (hasTag) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addRelevantDocuments() {
        LongStream.range(1, articlesWithTopicTagsRepository.count() + 1).boxed()
                .map(id -> articlesWithTopicTagsRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .peek(article -> {
                    if (article.getId() % 1000 == 0) {
                        LOG.info(String.format("Processing article %d", article.getId()));
                    }
                })
                .forEach(this::addRelevantDocument);
    }

    private void addRelevantDocument(RcvArticlesWithTopicTags queryArticle) {
        List<String> existingTagGroups = getExistingTagGroups(queryArticle);
        List<Long> relevantArticles = getArticlesWithTagGroups(existingTagGroups, queryArticle.getId());
        //System.out.println(queryArticle.getId());
        //System.out.println(relevantArticles);
        if (!relevantArticles.isEmpty()) {
            long randomId = relevantArticles.get(random.nextInt(relevantArticles.size()));

            Optional<RcvArticlesWithTopicTags> relevantArticleOpt = articlesWithTopicTagsRepository.findById(randomId);
            if (relevantArticleOpt.isPresent()) {
                RcvArticlesWithTopicTags relevantArticle = relevantArticleOpt.get();
                queryArticle.setRelevantId(relevantArticle.getId());
                articlesWithTopicTagsRepository.save(queryArticle);
            }
        }
    }

    /**
     * Get the names of the public properties which are set on queryArticle.
     * @param queryArticle
     * @return
     */
    private List<String> getExistingTagGroups(RcvArticles queryArticle) {
        Field[] fields = queryArticle.getClass().getFields();
        return Arrays.stream(fields)
                .filter(field -> hasField(field, queryArticle))
                .filter(field -> !field.getName().equals("queryArticleNGramIndices"))
                .filter(field -> !field.getName().equals("queryArticleWordIndices"))
                .filter(field -> !field.getName().equals("articleId"))
                .filter(field -> !field.getName().equals("relevantId"))
                .filter(field -> !field.getName().equals("id"))
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    List<Long> getArticlesWithTagGroups(List<String> tagGroups, long excludeId) {
        return tagGroups.stream()
                .flatMap(tagGroup -> reutersTagsToIdIndex.get(tagGroup).stream()
                        .filter(id -> !id.equals(excludeId)))
                .collect(Collectors.toList());
    }
}
