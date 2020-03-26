package com.braincourt;

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

    public DataProcessing(OneHotNgramVectors oneHotNgramVectors,
                          VocabularyExtractor vocabularyExtractor,
                          Processing processing) {
        this.oneHotNgramVectors = oneHotNgramVectors;
        this.vocabularyExtractor = vocabularyExtractor;
        this.processing = processing;
    }

    private static Logger LOG = LoggerFactory
            .getLogger(DataProcessing.class);

    public static void main(String[] args) {
        SpringApplication.run(DataProcessing.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO: Implement command line options for only processing certain datasets

        processing.preprocessDatasets();

        vocabularyExtractor.writeVocabularyToFile();

        oneHotNgramVectors.writeOneHotDatasetsToDatabase();
    }
}