package com.braincourt.onehotvectors;

import com.braincourt.onehotvectors.writers.database.DatabaseWriter;
import com.braincourt.onehotvectors.writers.database.DuplicateQuestionsDatabaseWriter;
import com.braincourt.onehotvectors.writers.database.NaturalQuestionsDatabaseWriter;
import com.braincourt.onehotvectors.writers.database.Rcv1ArticlesDatabaseWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This class should turn the dataset into n-gram word vectors.
 *
 */
@Component
public class OneHotNgramVectors {

    private String processedDataDir;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private int currentId = 0;
    private Map<String, Integer> nGramToId = new HashMap<>();

    static Map<String, Map<String, Integer>> wordToNGramToId = new HashMap<>();

    List<DatabaseWriter> WRITERS = new ArrayList<>();
    int nGramSize;
    public OneHotNgramVectors(@Value("${processed.data.dir}") String processedDataDir,
                              @Value("${ngram.size}") int nGramSize,
                              NaturalQuestionsDatabaseWriter naturalQuestionsDatabaseWriter,
                              DuplicateQuestionsDatabaseWriter duplicateQuestionsDatabaseWriter,
                              Rcv1ArticlesDatabaseWriter rcv1ArticlesDatabaseWriter) {
        this.nGramSize = nGramSize;
        this.processedDataDir = processedDataDir;
        WRITERS.addAll(Arrays.asList(
                naturalQuestionsDatabaseWriter,
                duplicateQuestionsDatabaseWriter,
                rcv1ArticlesDatabaseWriter));
    }

    public void writeOneHotDatasetsToDatabase() {
        constructNGramVocabulary(nGramSize);

        writeNGramsToFile();

        writeNGramRepresentations();
    }

    private void constructNGramVocabulary(int n) {

        getVocabulary().forEach(word -> {
            if (!wordToNGramToId.containsKey(word)) {

                getNGrams(n, word)
                        .forEach(nGram -> {

                            int nGramId;
                            if (!nGramToId.containsKey(nGram)) {

                                nGramToId.put(nGram, currentId);
                                nGramId = currentId++;

                            } else {

                                nGramId = nGramToId.get(nGram);

                            }

                            if (wordToNGramToId.containsKey(word)) {

                                wordToNGramToId.get(word).put(nGram, nGramId);

                            } else {

                                Map<String, Integer> nGramToId = new HashMap<>();
                                nGramToId.put(nGram, nGramId);
                                wordToNGramToId.put(word, nGramToId);

                            }
                        });
            }
        });
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeNGramsToFile() {
        try {
            bufferedWriter = new BufferedWriter(new java.io.FileWriter(processedDataDir + "/trigrams.txt"));
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

            bufferedReader = new BufferedReader(new FileReader(processedDataDir + "/vocabulary.txt")); // TODO: Extract all file paths and have them only in one place for each path
            return bufferedReader.lines().map(line -> line.split(" ")[0]);

        } catch (IOException e) {

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

    private void writeNGramRepresentations() {
        for (DatabaseWriter writer : WRITERS) {
            writer.write(wordToNGramToId);
        }
    }

    public static Map<String, Map<String, Integer>> getWordToNGramToId() {
        return wordToNGramToId;
    }
}
