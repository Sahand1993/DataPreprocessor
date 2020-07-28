package com.braincourt.onehotvectors.entitystreamers;

import com.braincourt.mysql.entities.NaturalQuestions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class NaturalQuestionAllStreamer extends NaturalQuestionStreamer {

    AtomicLong currentId = new AtomicLong(1);

    public NaturalQuestionAllStreamer(@Value("${processed.data.dir}") String preprocessedHome,
                                      @Value("${nq.folder}") String naturalQuestionsFolderName,
                                      @Value("${filename.json}") String fileName,
                                      @Value("${indices.delimiter}") String indicesDelimiter,
                                      @Value("${csv.delimiter}") String csvDelimiter){
        super(
                preprocessedHome + naturalQuestionsFolderName + fileName,
                csvDelimiter,
                indicesDelimiter
        );
    }

    @Override
    public List<NaturalQuestions> createEntities(JsonObject dataRow) {
        NaturalQuestions naturalQuestion = new NaturalQuestions();
        naturalQuestion
                .setQuestionNGramIndices(getQuestionNGramIndices(dataRow))
                .setDocumentNGramIndices(getDocumentNGramIndices(dataRow))
                .setQuestionWordIndices(getQuestionWordIndices(dataRow))
                .setDocumentWordIndices(getDocumentWordIndices(dataRow))
                .setTitleNGramIndices(getTitleNGramIndices(dataRow))
                .setTitleWordIndices(getTitleWordIndices(dataRow))
                .setId(dataRow.get("id").getAsString())
                .setExampleId(dataRow.get("exampleId").getAsString());
        return Collections.singletonList(naturalQuestion);
    }

    protected String getDocumentNGramIndices(JsonObject dataRow) {
        List<String> tokens = getDocumentTokens(dataRow);
        return joinIdsWithFreqs(getNGramIndicesWithFreqs(tokens));
    }

    public static List<String> getDocumentTokens(JsonObject dataRow) {
        return getAsList(dataRow.get("documentTokens").getAsJsonArray()).stream()
                .map(JsonElement::getAsString)
                .collect(Collectors.toList());
    }

    protected String getDocumentWordIndices(JsonObject dataRow) {
        List<String> tokens = getDocumentTokens(dataRow);
        return joinIdsWithFreqs(getWordIndicesWithFreqs(tokens));
    }
}
