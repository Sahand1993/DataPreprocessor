package com.braincourt.onehotvectors;

import com.braincourt.onehotvectors.writers.Writer;
import com.braincourt.onehotvectors.writers.database.ngram.DuplicateQuestionsDatabaseWriter;
import com.braincourt.onehotvectors.writers.database.ngram.NaturalQuestionsDatabaseWriter;
import com.braincourt.onehotvectors.writers.database.ngram.Rcv1ArticlesDatabaseWriter;
import com.braincourt.onehotvectors.writers.file.ngram.NaturalQuestionsNgramIndicesFileWriter;
import com.braincourt.onehotvectors.writers.file.ngram.DuplicateQuestionsNgramIndicesFileWriter;
import com.braincourt.onehotvectors.writers.file.ngram.Rcv1ArticlesNgramIndicesFileWriter;
import com.braincourt.onehotvectors.writers.file.wordindices.DuplicateQuestionsWordIndicesFileWriter;
import com.braincourt.onehotvectors.writers.file.wordindices.NaturalQuestionsWordIndicesFileWriter;
import com.braincourt.onehotvectors.writers.file.wordindices.Rcv1ArticlesVocabularyFileWriter;
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

    static Map<String, Map<String, Integer>> wordsToNGramsWithIds = new HashMap<>();

    static Map<String, String> vocabulary = new HashMap<>();

    List<Writer> WRITERS = new ArrayList<>();

    int nGramSize;

    public OneHotNgramVectors(@Value("${processed.data.dir}") String processedDataDir,
                              @Value("${ngram.size}") int nGramSize,
                              DuplicateQuestionsNgramIndicesFileWriter duplicateQuestionsNgramIndicesFileWriter,
                              NaturalQuestionsNgramIndicesFileWriter naturalQuestionsNgramIndicesFileWriter,
                              Rcv1ArticlesNgramIndicesFileWriter rcv1ArticlesNgramFileWriter,

                              NaturalQuestionsDatabaseWriter naturalQuestionsDatabaseWriter,
                              DuplicateQuestionsDatabaseWriter duplicateQuestionsDatabaseWriter,
                              Rcv1ArticlesDatabaseWriter rcv1ArticlesDatabaseWriter,

                              DuplicateQuestionsWordIndicesFileWriter duplicateQuestionsWordIndicesFileWriter,
                              NaturalQuestionsWordIndicesFileWriter naturalQuestionsWordIndicesFileWriter,
                              Rcv1ArticlesVocabularyFileWriter rcv1ArticlesVocabularyFileWriter) {
        this.nGramSize = nGramSize;
        this.processedDataDir = processedDataDir;
        WRITERS.addAll(Arrays.asList(
                duplicateQuestionsNgramIndicesFileWriter,
                naturalQuestionsNgramIndicesFileWriter,
                rcv1ArticlesNgramFileWriter,
                naturalQuestionsDatabaseWriter,
                duplicateQuestionsDatabaseWriter,
                rcv1ArticlesDatabaseWriter,
                duplicateQuestionsWordIndicesFileWriter,
                naturalQuestionsWordIndicesFileWriter,
                rcv1ArticlesVocabularyFileWriter));
    }

    public void writeOneHotDatasets() {
        constructNGramVocabulary(nGramSize);

        writeNGramsToFile();

        write();
    }

    private void constructNGramVocabulary(int n) {
        constructVocabulary();
        vocabulary.keySet().forEach(word -> {
            if (!wordsToNGramsWithIds.containsKey(word)) {

                getNGrams(n, word)
                        .forEach(nGram -> {

                            int nGramId;
                            if (!nGramToId.containsKey(nGram)) {

                                nGramToId.put(nGram, currentId);
                                nGramId = currentId++;

                            } else {

                                nGramId = nGramToId.get(nGram);

                            }

                            if (wordsToNGramsWithIds.containsKey(word)) {

                                wordsToNGramsWithIds.get(word).put(nGram, nGramId);

                            } else {

                                Map<String, Integer> nGramToId = new HashMap<>();
                                nGramToId.put(nGram, nGramId);
                                wordsToNGramsWithIds.put(word, nGramToId);

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

    private void constructVocabulary() {

        try {

            bufferedReader = new BufferedReader(new FileReader(processedDataDir + "/vocabulary.txt")); // TODO: Extract all file paths and have them only in one place for each path
            bufferedReader.lines().forEach(line -> {
                String[] wordAndId = line.split(" ");
                vocabulary.put(wordAndId[0], wordAndId[1]);
            });

        } catch (IOException e) {

            e.printStackTrace();

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

    private void write() {
        for (Writer writer : WRITERS) {
            writer.write();
        }
    }

    public static Map<String, Map<String, Integer>> getWordsToNGramsWithIds() {
        return wordsToNGramsWithIds;
    }

    public static Map<String, String> getVocabulary() {
        return vocabulary;
    }
}
