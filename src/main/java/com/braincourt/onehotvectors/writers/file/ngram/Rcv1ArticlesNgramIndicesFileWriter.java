package com.braincourt.onehotvectors.writers.file.ngram;

import com.braincourt.mysql.entities.RcvArticles;
import com.braincourt.onehotvectors.entitystreamers.ngramindices.Rcv1ArticleNgramIndicesStreamer;
import com.braincourt.onehotvectors.writers.ReutersTagsToIdIndex;
import com.braincourt.onehotvectors.writers.file.FileWriter;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


@Component
public class Rcv1ArticlesNgramIndicesFileWriter extends FileWriter<RcvArticles> {

    private final ReutersTagsToIdIndex reutersTagsToIdIndex;

    private Gson gson = new Gson();

    public Rcv1ArticlesNgramIndicesFileWriter(Rcv1ArticleNgramIndicesStreamer streamer,
                                              @Value("${reuters.home}") String datasetHome,
                                              @Value("${onehot.filename.csv}") String filename,
                                              @Value("${ngram.size}") int ngramSize,
                                              ReutersTagsToIdIndex reutersTagsToIdIndex) {
        super(streamer, datasetHome + String.format(filename, ngramSize), RcvArticles.class);
        this.reutersTagsToIdIndex = reutersTagsToIdIndex;
    }

    @Override
    public void write() {
        super.write();
        addRelevant();
    }

    private void addRelevant() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(outFilePath));
            bufferedReader.lines()
                    .forEach(line -> {
                        System.out.println(line); // TODO
                    });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

