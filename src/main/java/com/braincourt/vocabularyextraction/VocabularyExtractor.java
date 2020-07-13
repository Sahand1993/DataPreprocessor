package com.braincourt.vocabularyextraction;

import com.braincourt.vocabularyextraction.wordstreamers.ConfluenceWordStreamer;
import com.braincourt.vocabularyextraction.wordstreamers.NaturalQuestionsDocumentWordStreamer;
import com.braincourt.vocabularyextraction.wordstreamers.NaturalQuestionsTitleWordStreamer;
import com.braincourt.vocabularyextraction.wordstreamers.QuoraWordStreamer;
import com.braincourt.vocabularyextraction.wordstreamers.ReutersWordStreamer;
import com.braincourt.vocabularyextraction.wordstreamers.SquadWordStreamer;
import com.braincourt.vocabularyextraction.wordstreamers.WikiQAWordStreamer;
import com.braincourt.vocabularyextraction.wordstreamers.WordStreamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * This class is responsible for writing the whole vocabulary of all data sets to a file on disk.
 * The file is at $THESIS_PROCESSED_DATA_DIR:vocabulary.txt, with one line per unique word
 */
@Component
public class VocabularyExtractor {

    private static Logger LOG = LoggerFactory.getLogger(VocabularyExtractor.class);

    public String preprocessedDataDir;

    private Map<String, Integer> vocabulary;

    private List<WordStreamer> wordStreamers;

    private int wordFrequencyRequirement;

    public VocabularyExtractor(@Value("${processed.data.dir}") String preprocessedDataDir,
                               @Value("${vocabulary.remove.words.fewer.than}") int wordFrequencyRequirement,
                               ConfluenceWordStreamer confluenceWordStreamer,
                               QuoraWordStreamer quoraWordStreamer,
                               ReutersWordStreamer reutersWordStreamer,
                               NaturalQuestionsTitleWordStreamer naturalQuestionsTitleWordStreamer,
                               NaturalQuestionsDocumentWordStreamer naturalQuestionsDocumentWordStreamer,
                               SquadWordStreamer squadWordStreamer,
                               WikiQAWordStreamer wikiQAWordStreamer) {
        this.preprocessedDataDir = preprocessedDataDir;
        wordStreamers = Arrays.asList(
                confluenceWordStreamer,
                wikiQAWordStreamer,
                squadWordStreamer,
                quoraWordStreamer,
                reutersWordStreamer,
                naturalQuestionsTitleWordStreamer,
                naturalQuestionsDocumentWordStreamer);
        this.wordFrequencyRequirement = wordFrequencyRequirement;
    }

    public void createVocabulary() {
        extractVocabulary();
        filterVocabulary();
        AtomicInteger id = new AtomicInteger();
        try {

            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(preprocessedDataDir + "/vocabulary.txt"));
            vocabulary.forEach((word, count) -> {
                try {
                    bufferedWriter.write(id.getAndIncrement() + " " + word + " " + count);
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

    private void filterVocabulary() {
        vocabulary = vocabulary.entrySet().stream()
                .filter(entry -> entry.getValue() > wordFrequencyRequirement)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void extractVocabulary() {
        vocabulary = new HashMap<>();
        AtomicInteger i = new AtomicInteger();
        i.getAndIncrement();
        wordStreamers.stream().flatMap(WordStreamer::getWordStream)
                .forEach(word -> {
                    if (i.get() % 2000000 == 0) {
                        LOG.info(String.format("processing word %d: %s", i.get(), word));
                        LOG.info(String.format("vocabulary size: %d\n", vocabulary.size()));
                    }
                    vocabulary.put(word, vocabulary.containsKey(word) ? vocabulary.get(word) + 1 : 1);
                    i.getAndIncrement();
                });
    }

}
