package com.braincourt;

import com.braincourt.onehotvectors.OneHotNgramVectors;
import com.braincourt.preprocessing.Processing;
import com.braincourt.vocabularyextraction.VocabularyExtractor;

public class Main {

    public static void main(String[] args) {
        // TODO: Implement command line options for only processing certain datasets

        Processing processing = new Processing(System.getenv("THESIS_DATA_DIR"));
        processing.preprocessDatasets();

        VocabularyExtractor vocabularyExtractor = new VocabularyExtractor(System.getenv("THESIS_PROCESSED_DATA_DIR"));
        vocabularyExtractor.writeVocabularyToFile();

        OneHotNgramVectors oneHotTrigramVectors = new OneHotNgramVectors(System.getenv("THESIS_PROCESSED_DATA_DIR"), 3);
        oneHotTrigramVectors.createOneHotDatasets();
    }
}