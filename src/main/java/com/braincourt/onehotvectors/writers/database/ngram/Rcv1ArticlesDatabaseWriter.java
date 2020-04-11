package com.braincourt.onehotvectors.writers.database.ngram;

import com.braincourt.mysql.entities.RcvArticles;
import com.braincourt.mysql.entities.RcvArticlesWithTopicTags;
import com.braincourt.mysql.entities.RcvArticlesWithoutTopicTags;
import com.braincourt.mysql.repositories.RcvArticlesWithTopicTagsRepository;
import com.braincourt.mysql.repositories.RcvArticlesWithoutTopicTagsRepository;
import com.braincourt.onehotvectors.entitystreamers.ngramindices.Rcv1ArticleNgramIndicesStreamer;
import com.braincourt.onehotvectors.writers.ReutersTagsToIdIndex;
import com.braincourt.onehotvectors.writers.database.DatabaseWriter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Component
public class Rcv1ArticlesDatabaseWriter extends DatabaseWriter<RcvArticles> {

    RcvArticlesWithoutTopicTagsRepository articlesWithoutTopicTagsRepository;
    RcvArticlesWithTopicTagsRepository articlesWithTopicTagsRepository;

    ReutersTagsToIdIndex reutersTagsToIdIndex;

    Random random = new Random(1);

    public Rcv1ArticlesDatabaseWriter(
            Rcv1ArticleNgramIndicesStreamer rcv1ArticleNgramIndicesStreamer,
            RcvArticlesWithoutTopicTagsRepository articlesWithoutTopicTagsRepository,
            RcvArticlesWithTopicTagsRepository articlesWithTopicTagsRepository,
            ReutersTagsToIdIndex reutersTagsToIdIndex) {
        this.streamer = rcv1ArticleNgramIndicesStreamer;
        this.articlesWithoutTopicTagsRepository = articlesWithoutTopicTagsRepository;
        this.articlesWithTopicTagsRepository = articlesWithTopicTagsRepository;
        this.reutersTagsToIdIndex = reutersTagsToIdIndex;
    }

    @Override
    public void write() {
        LOG.info("Started writing to database");
        writeToDatabase();
        LOG.info("Finished writing to database");

        // create tags to articles hashmap
        LOG.info("Creating index");
        createIndex(); // TODO: Put in own processing step class
        LOG.info("Finished index");

        LOG.info("Started adding relevant documents to RCV1 dataset...");
        addRelevantDocuments(); // TODO: Put in own processing step class
        LOG.info("Finished adding relevant documents to RCV1 dataset");
    }

    private void createIndex() {
        LongStream.range(1, articlesWithTopicTagsRepository.count() + 1).boxed()
                .map(id -> articlesWithTopicTagsRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                //.limit(1000)
                .peek(article -> {
                    if (article.getId() % 1000 == 0) {
                        LOG.info(String.format("Adding %dth article to index", article.getId()));
                    }
                })
                .forEach(this::addToIndex);
    }

    private void addToIndex(RcvArticles article) {
        Class clazz = article.getClass();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (hasField(field, article)) {
                addToIndex(field.getName(), article.getId());
            }
        }
    }

    private void writeToDatabase() {
        AtomicLong i = new AtomicLong();
        this.streamer.getEntities().forEach(entity -> {
            if (entity instanceof RcvArticlesWithTopicTags) {
                articlesWithTopicTagsRepository.save((RcvArticlesWithTopicTags) entity);
            } else {
                articlesWithoutTopicTagsRepository.save((RcvArticlesWithoutTopicTags) entity);
            }
            if (i.incrementAndGet() % 1000 == 0) {
                LOG.info(i.get() + " entities processed.");
            }
        });
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
        if (!relevantArticles.isEmpty()) {
            Long randomId = relevantArticles.get(random.nextInt(relevantArticles.size()));

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
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    List<Long> getArticlesWithTagGroups(List<String> tagGroups, Long excludeId) {
        return tagGroups.stream()
                .flatMap(tagGroup -> reutersTagsToIdIndex.get(tagGroup).stream().filter(id -> !id.equals(excludeId)))
                .collect(Collectors.toList());
    }
}
