package com.braincourt.onehotvectors;

import com.braincourt.onehotvectors.writers.NaturalQuestionsWriter;
import com.braincourt.onehotvectors.writers.QuoraWriter;
import com.braincourt.onehotvectors.writers.ReutersWriter;
import com.braincourt.onehotvectors.writers.Writer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.braincourt.Constants.*;

/**
 * This class should turn the dataset into n-gram word vectors.
 *
 */
public class OneHotNgramVectors {

    private String processedDatasetDir;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private int currentId = 0;
    private Map<String, Integer> nGramToId = new HashMap<>();
    private Map<String, Map<String, Integer>> wordToNGrams = new HashMap<>();
    List<Writer> WRITERS = new ArrayList<>();
    int N;

    public OneHotNgramVectors(String processedDatasetDir, int N) {
        this.N = N;
        this.processedDatasetDir = processedDatasetDir;
        WRITERS.addAll(Arrays.asList(
                new QuoraWriter(PREPROCESSED_QUORA_DATA, PREPROCESSED_QUORA, N),
                new NaturalQuestionsWriter(PREPROCESSED_NQ_DATA, PREPROCESSED_NQ, N),
                new ReutersWriter(PREPROCESSED_REUTERS_DATA, PREPROCESSED_REUTERS, N)));
    }

    public void createOneHotDatasets() {
        try {

            constructNGramVocabulary(N);
            writeNGramsToFile();
            bufferedReader.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        writeOneHotRepresentations();

    }

    private void constructNGramVocabulary(int n) {
        getVocabulary().forEach(word -> {
            if (!wordToNGrams.containsKey(word)) {

                getNGrams(n, word)
                        .forEach(nGram -> {

                            int nGramId;
                            if (!nGramToId.containsKey(nGram)) {

                                nGramToId.put(nGram, currentId);
                                nGramId = currentId++;

                            } else {

                                nGramId = nGramToId.get(nGram);

                            }

                            if (wordToNGrams.containsKey(word)) {

                                wordToNGrams.get(word).put(nGram, nGramId);

                            } else {

                                Map<String, Integer> nGramToId = new HashMap<>();
                                nGramToId.put(nGram, nGramId);
                                wordToNGrams.put(word, nGramToId);

                            }
                        });
            }
        });

    }

    private void writeNGramsToFile() {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(processedDatasetDir + "/trigrams.txt"));
            bufferedWriter.write("trigram id");
            bufferedWriter.newLine();

            nGramToId.forEach((nGram, id) -> {
                    try {

                        bufferedWriter.write(nGram + " " + id);
                        bufferedWriter.newLine();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }
                });
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stream<String> getVocabulary() {

        try {

            bufferedReader = new BufferedReader(new FileReader(processedDatasetDir + "/vocabulary.txt")); // TODO: Extract all file paths and have them only in one place for each path
            return bufferedReader.lines().map(line -> line.split(" ")[0]);

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return Stream.empty();

        }
    }

    private Stream<String> getNGrams(int n, String word) {
        String paddedWord = "^" + word + "$";
        List<String> nGrams = new ArrayList<>();

        for (int i = 0; i < paddedWord.length() - n + 1; i++) {

                nGrams.add(paddedWord.substring(i, i + n));

        }

        return nGrams.stream();
    }

    private void writeOneHotRepresentations() {
        for (Writer writer : WRITERS) {
            writer.write(wordToNGrams);
        }
    }


}
