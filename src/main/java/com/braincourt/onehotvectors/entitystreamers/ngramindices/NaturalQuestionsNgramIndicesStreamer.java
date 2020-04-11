package com.braincourt.onehotvectors.entitystreamers.ngramindices;


import com.braincourt.onehotvectors.entitystreamers.NaturalQuestionStreamer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NaturalQuestionsNgramIndicesStreamer extends NaturalQuestionStreamer {

    public NaturalQuestionsNgramIndicesStreamer(@Value("${naturalQuestions.home}") String datasetHome,
                                                @Value("${filename.json}") String jsonFileName,
                                                @Value("${csv.delimiter}") String csvDelimiter,
                                                @Value("${indices.delimiter}") String indicesDelimiter) {
        super(datasetHome + jsonFileName, csvDelimiter, indicesDelimiter);
    }

    protected String getQuestionIndices(JsonObject dataRow) {
        return getNgramIndices(dataRow, "questionTokens");
    }

    protected String getDocumentIndices(JsonObject dataRow) {
        List<String> tokens = getDocumentTokens(dataRow);
        return String.join(indicesDelimiter, getNGramIndices(tokens));
    }
}
