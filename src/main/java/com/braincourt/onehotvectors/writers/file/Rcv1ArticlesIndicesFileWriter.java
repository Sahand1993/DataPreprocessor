package com.braincourt.onehotvectors.writers.file;

import com.braincourt.mysql.entities.RcvArticles;
import com.braincourt.mysql.entities.RcvArticlesWithTopicTags;
import com.braincourt.mysql.repositories.RcvArticlesWithTopicTagsRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Component
public class Rcv1ArticlesIndicesFileWriter extends FileWriter<RcvArticles> {

    private final String outFileName;
    private final String datasetHome;
    protected RcvArticlesWithTopicTagsRepository repository;
    protected Gson gson = new Gson();

    public Rcv1ArticlesIndicesFileWriter(RcvArticlesWithTopicTagsRepository repository,
                                         @Value("${processed.data.dir}") String preprocessedHome,
                                         @Value("${reuters.folder}") String reutersFolderName,
                                         @Value("${indices.filename.json}") String filename,
                                         @Value("${outfile.json}") String outFileName,
                                         @Value("${reuters.validationPercentage}") int validationPercentage) {
        super(RcvArticles.class);
        this.repository = repository;
        this.datasetHome = preprocessedHome + reutersFolderName;
        this.outFileName = outFileName;
    }

    @Override
    public void write() {
        List<Long> ids = LongStream.range(1, repository.count() + 1).boxed().collect(Collectors.toList());
        Collections.shuffle(ids);
        writeIdsToPath(ids, datasetHome + outFileName);
    }


    private void writeIdsToPath(List<Long> ids, String path) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(path));
            ids.forEach(id -> {
                Optional<RcvArticlesWithTopicTags> rcvArticle = repository.findById(Long.toString(id));
                rcvArticle.ifPresent(article -> {
                    try {
                        bufferedWriter.write(gson.toJson(article));
                        bufferedWriter.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                if (linesWritten.incrementAndGet() % 1000 == 0) {
                    LOG.info(String.format("Went through %d articles.", linesWritten.get()));
                }
            });
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
