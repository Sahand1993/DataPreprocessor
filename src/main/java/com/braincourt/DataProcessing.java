package com.braincourt;

import com.braincourt.datasetdividers.Dividing;
import com.braincourt.onehotvectors.OneHotNgramVectors;
import com.braincourt.preprocessing.Processing;
import com.braincourt.vocabularyextraction.VocabularyExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DataProcessing implements CommandLineRunner {

    OneHotNgramVectors oneHotNgramVectors;
    VocabularyExtractor vocabularyExtractor;
    Processing processing;
    Dividing dividing;

    public DataProcessing(OneHotNgramVectors oneHotNgramVectors,
                          VocabularyExtractor vocabularyExtractor,
                          Processing processing,
                          Dividing dividing) {
        this.oneHotNgramVectors = oneHotNgramVectors;
        this.vocabularyExtractor = vocabularyExtractor;
        this.processing = processing;
        this.dividing = dividing;
    }

    private static Logger LOG = LoggerFactory
            .getLogger(DataProcessing.class);

    public static void main(String[] args) {
        SpringApplication.run(DataProcessing.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        processing.preprocessDatasets();

        vocabularyExtractor.createVocabulary();

        oneHotNgramVectors.writeOneHotDatasets();
    }
}