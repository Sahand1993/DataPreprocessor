package com.braincourt.onehotvectors;

import com.braincourt.datasetdividers.Dividing;
import com.braincourt.onehotvectors.writers.Writer;
import com.braincourt.onehotvectors.writers.database.Rcv1ArticlesDatabaseWriter;
import com.braincourt.onehotvectors.writers.file.ConfluenceFileWriter;
import com.braincourt.onehotvectors.writers.file.DuplicateQuestionsIndicesFileWriter;
import com.braincourt.onehotvectors.writers.file.NaturalQuestionsAllIndicesFileWriter;
import com.braincourt.onehotvectors.writers.file.Rcv1ArticlesIndicesFileWriter;
import com.braincourt.onehotvectors.writers.file.SquadIndicesFileWriter;
import com.braincourt.onehotvectors.writers.file.WikiQAIndicesFileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    static Map<String, Map<Integer, Integer>> wordsToNGramIdsToFreqs = new HashMap<>();

    static Map<String, String> vocabulary = new HashMap<>();

    List<Writer> WRITERS = new ArrayList<>();

    int nGramSize;

    Logger LOG = LoggerFactory.getLogger(this.getClass());


    public OneHotNgramVectors(@Value("${processed.data.dir}") String processedDataDir,
                              @Value("${ngram.size}") int nGramSize,

                              ConfluenceFileWriter confluenceFileWriter
//                              Rcv1ArticlesDatabaseWriter rcv1ArticlesDatabaseWriter,
//                              DuplicateQuestionsIndicesFileWriter duplicateQuestionsIndicesFileWriter,
//                              NaturalQuestionsAllIndicesFileWriter naturalQuestionsAllIndicesFileWriter,
//                              Rcv1ArticlesIndicesFileWriter rcv1ArticlesIndicesFileWriter,
//                              SquadIndicesFileWriter squadIndicesFileWriter,
//                              WikiQAIndicesFileWriter wikiQAIndicesFileWriter
    ) {
        this.nGramSize = nGramSize;
        this.processedDataDir = processedDataDir;
        WRITERS.addAll(Arrays.asList(
                confluenceFileWriter
//                wikiQAIndicesFileWriter,
//                squadIndicesFileWriter,
//                duplicateQuestionsIndicesFileWriter,
//                naturalQuestionsAllIndicesFileWriter,
//                rcv1ArticlesDatabaseWriter,
//                rcv1ArticlesIndicesFileWriter
                ));
    }

    public void writeOneHotDatasets() {
        LOG.info("Constructing vocabulary.");
        constructVocabulary();

        LOG.info("Constructing ngram vocabulary.");
        constructNGramVocabulary(nGramSize);

        LOG.info("Writing ngrams to file.");
        writeNGramsToFile();

        write();
    }

    private void constructNGramVocabulary(int n) {
        LOG.info("Could not find ngram indices, indexing...");
        vocabulary.keySet().forEach(word -> {
            if (!wordsToNGramIdsToFreqs.containsKey(word)) {

                getNGrams(n, word)
                        .forEach(nGram -> {

                            int nGramId;
                            if (!nGramToId.containsKey(nGram)) {

                                nGramToId.put(nGram, currentId);
                                nGramId = currentId++;

                            } else {

                                nGramId = nGramToId.get(nGram);

                            }

                            if (wordsToNGramIdsToFreqs.containsKey(word)) {

                                Map<Integer, Integer> nGramIdsToFreqs = wordsToNGramIdsToFreqs.get(word);
                                if (nGramIdsToFreqs.containsKey(nGramId)) {

                                    nGramIdsToFreqs.put(nGramId, nGramIdsToFreqs.get(nGramId) + 1);

                                } else {

                                    nGramIdsToFreqs.put(nGramId, 1);

                                }

                            } else {

                                Map<Integer, Integer> nGramIdToFreq = new HashMap<>();
                                nGramIdToFreq.put(nGramId, 1);
                                wordsToNGramIdsToFreqs.put(word, nGramIdToFreq);

                            }
                        });
            }
        });
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
                vocabulary.put(wordAndId[1], wordAndId[0]);
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

    public static Map<String, Map<Integer, Integer>> getWordsToNGramIdsToFreqs() {
        return wordsToNGramIdsToFreqs;
    }

    public static Map<String, String> getVocabulary() {
        return vocabulary;
    }
}
