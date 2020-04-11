package com.braincourt.onehotvectors.entitystreamers.wordindices;

import com.braincourt.onehotvectors.entitystreamers.NaturalQuestionStreamer;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NaturalQuestionWordIndicesStreamer extends NaturalQuestionStreamer {

    public NaturalQuestionWordIndicesStreamer(@Value("${naturalQuestions.home}") String datasetHome,
                                              @Value("${filename.json}") String jsonFileName,
                                              @Value("${csv.delimiter}") String csvDelimiter,
                                              @Value("${indices.delimiter}") String indicesDelimiter) {
        super(datasetHome + jsonFileName, csvDelimiter, indicesDelimiter);
    }

    @Override
    protected String getQuestionIndices(JsonObject dataRow) {
        return getWordIndices(dataRow, "questionTokens");
    }

    @Override
    protected String getDocumentIndices(JsonObject dataRow) {
        List<String> tokens = getDocumentTokens(dataRow);
        return String.join(indicesDelimiter, getWordIndices(tokens));
    }
}
