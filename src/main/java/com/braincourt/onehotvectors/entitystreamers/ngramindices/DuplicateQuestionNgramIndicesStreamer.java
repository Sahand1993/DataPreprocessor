package com.braincourt.onehotvectors.entitystreamers.ngramindices;

import com.braincourt.onehotvectors.entitystreamers.DuplicateQuestionStreamer;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DuplicateQuestionNgramIndicesStreamer extends DuplicateQuestionStreamer {

    private static Logger LOG = LoggerFactory.getLogger(DuplicateQuestionNgramIndicesStreamer.class);

    public DuplicateQuestionNgramIndicesStreamer(@Value("${quora.home}") String datasetHome,
                                                 @Value("${filename.json}") String jsonFileName,
                                                 @Value("${csv.delimiter}") String csvDelimiter,
                                                 @Value("${indices.delimiter}") String indicesDelimiter) {
        super(datasetHome + jsonFileName, csvDelimiter, indicesDelimiter);
    }

    protected String getQuestion1Indices(JsonObject json) {
        return getJoinedNGramIndicesFrom(json, "question1_tokens");
    }

    protected String getQuestion2Indices(JsonObject json) {
        return getJoinedNGramIndicesFrom(json, "question2_tokens");
    }

    private String getJoinedNGramIndicesFrom(JsonObject json, String jsonKey) {
        return getNgramIndices(json, jsonKey);
    }
}
