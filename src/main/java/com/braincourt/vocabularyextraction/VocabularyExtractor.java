package com.braincourt.vocabularyextraction;

import com.braincourt.vocabularyextraction.wordstreamers.NaturalQuestionsWordStreamer;
import com.braincourt.vocabularyextraction.wordstreamers.QuoraWordStreamer;
import com.braincourt.vocabularyextraction.wordstreamers.ReutersWordStreamer;
import com.braincourt.vocabularyextraction.wordstreamers.WordStreamer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * This class is responsible for writing the whole vocabulary of all data sets to a file on disk.
 * The file is at $THESIS_PROCESSED_DATA_DIR:vocabulary.txt, with one line per unique word
 */
public class VocabularyExtractor {

    public static String preprocessedDataDir;

    Map<String, Integer> vocabulary;

    WordStreamer quoraWordStreamer;
    WordStreamer reutersWordStreamer;
    WordStreamer naturalQuestionsWordStreamer ;

    public VocabularyExtractor(String preprocessedDataDir) {
        this.preprocessedDataDir = preprocessedDataDir;
        quoraWordStreamer = new QuoraWordStreamer();
        reutersWordStreamer = new ReutersWordStreamer();
        naturalQuestionsWordStreamer = new NaturalQuestionsWordStreamer();
    }

    public void writeVocabularyToFile() {
        extractVocabulary();
        try {

            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(preprocessedDataDir + "/vocabulary.txt"));
            vocabulary.forEach((word, count) -> {
                try {
                    bufferedWriter.write(word + " " + count);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            });
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void extractVocabulary() {
        vocabulary = new HashMap<>();
        AtomicInteger i = new AtomicInteger();
        i.getAndIncrement();
        // get a word-stream (all words, not just unique) from each dataset. Filter it here
        Stream.concat(quoraWordStreamer.getWordStream(), Stream.concat(reutersWordStreamer.getWordStream(),
                                                                       naturalQuestionsWordStreamer.getWordStream()))
                .forEach(word -> {
                    if (i.get() % 2000000 == 0) {
                        System.out.println(String.format("processing word %d: %s", i.get(), word));
                        System.out.println(String.format("vocabulary size: %d\n", vocabulary.size()));

                    }
                    vocabulary.put(word, vocabulary.containsKey(word) ? vocabulary.get(word) + 1 : 1);
                    i.getAndIncrement();
                });
    }

}
