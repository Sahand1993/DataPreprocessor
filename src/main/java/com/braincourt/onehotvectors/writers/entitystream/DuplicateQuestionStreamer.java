package com.braincourt.onehotvectors.writers.entitystream;

import com.braincourt.mysql.entities.DuplicateQuestions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;

@Component
public class DuplicateQuestionStreamer extends EntityStreamer<DuplicateQuestions> {

    @Value("${quora.csvHeader}")
    String csvHeader;

    @Value("${ngram.delimiter}")
    String nGramDelimiter;

    private static Logger LOG = LoggerFactory.getLogger(DuplicateQuestionStreamer.class);

    public DuplicateQuestionStreamer(
            @Value("${quora.home}") String datasetHome,
            @Value("${filename.json}") String jsonFileName,
            @Value("${ngram.size}") int nGramSize,
            @Value("${quora.csvHeader}") String csvHeader) {
        this.jsonDataPath = datasetHome + jsonFileName;
        this.csvHeader = String.format(csvHeader, nGramSize, nGramSize);
    }


    /**
     * Creates a dataRow object containing the one-hot representation of dataRow.
     * @param duplicateQuestionJson
     * @return
     */
    @Override
    public Optional<DuplicateQuestions> createEntity(JsonObject duplicateQuestionJson) {
        boolean isDuplicate = duplicateQuestionJson.get("isDuplicate").getAsBoolean();
        if (!isDuplicate) {
            return Optional.empty();
        }

        DuplicateQuestions duplicateQuestions = new DuplicateQuestions();
        duplicateQuestions.setQuestion1NGrams(getQuestion1NGrams(duplicateQuestionJson));
        duplicateQuestions.setQuestion2NGrams(getQuestion2NGrams(duplicateQuestionJson));
        duplicateQuestions.setIsDuplicate(true);

        return Optional.of(duplicateQuestions);
    }

    private String getQuestion1NGrams(JsonObject json) {
        return getJoinedNGramIdsFrom(json, "question1_tokens", nGramDelimiter);
    }

    private String getQuestion2NGrams(JsonObject json) {
        return getJoinedNGramIdsFrom(json, "question2_tokens", nGramDelimiter);
    }

    private String getJoinedNGramIdsFrom(JsonObject json, String jsonKey, String delimiter) {
        List<String> tokens = getListFrom(json, jsonKey);
        SortedSet<String> nGramIds = getNGramIds(tokens);
        return String.join(delimiter, nGramIds);
    }

    private List<String> getListFrom(JsonObject json, String key) {
        return getAsList(json.getAsJsonArray(key)).stream()
                .map(JsonElement::getAsString)
                .collect(Collectors.toList());
    }




}
