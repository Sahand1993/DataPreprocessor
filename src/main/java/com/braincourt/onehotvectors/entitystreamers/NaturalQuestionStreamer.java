package com.braincourt.onehotvectors.entitystreamers;

import com.braincourt.mysql.entities.NaturalQuestions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class NaturalQuestionStreamer extends EntityStreamer<NaturalQuestions> {

    public NaturalQuestionStreamer(String jsonDataPath, String csvDelimiter, String indicesDelimiter) {
        super(jsonDataPath, csvDelimiter, indicesDelimiter);
    }

    protected String getQuestionNGramIndices(JsonObject dataRow) {
        return getNgramIndices(dataRow, "questionTokens");
    }

    protected String getQuestionWordIndices(JsonObject dataRow) {
        return getWordIndicesWithFreqs(dataRow, "questionTokens");
    }

    protected String getTitleNGramIndices(JsonObject dataRow) {
        JsonArray tokensArray = dataRow.get("titleTokens").getAsJsonArray();
        List<String> tokens = StreamSupport.stream(tokensArray.spliterator(), false)
                .map(JsonElement::getAsString)
                .collect(Collectors.toList());
        return joinIdsWithFreqs(getNGramIndicesWithFreqs(tokens));
    }

    protected String getTitleWordIndices(JsonObject dataRow) {
        return getWordIndicesWithFreqs(dataRow, "titleTokens");
    }
}
