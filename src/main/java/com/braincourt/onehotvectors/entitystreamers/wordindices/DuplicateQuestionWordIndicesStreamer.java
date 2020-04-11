package com.braincourt.onehotvectors.entitystreamers.wordindices;

import com.braincourt.onehotvectors.entitystreamers.DuplicateQuestionStreamer;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DuplicateQuestionWordIndicesStreamer extends DuplicateQuestionStreamer {

    public DuplicateQuestionWordIndicesStreamer(@Value("${quora.home}") String datasetHome,
                                                @Value("${filename.json}") String jsonFileName,
                                                @Value("${csv.delimiter}") String csvDelimiter,
                                                @Value("${indices.delimiter}") String indicesDelimiter) {
        super(datasetHome + jsonFileName, csvDelimiter, indicesDelimiter);
    }

    @Override
    protected String getQuestion1Indices(JsonObject json) {
        return getJoinedWordIndicesFrom(json, "question1_tokens");
    }

    @Override
    protected String getQuestion2Indices(JsonObject json) {
        return getJoinedWordIndicesFrom(json, "question2_tokens");
    }

    private String getJoinedWordIndicesFrom(JsonObject json, String jsonKey) {
        return getWordIndices(json, jsonKey);
    }
}
