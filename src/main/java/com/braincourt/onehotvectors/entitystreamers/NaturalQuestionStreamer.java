package com.braincourt.onehotvectors.entitystreamers;

import com.braincourt.mysql.entities.NaturalQuestions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public abstract class NaturalQuestionStreamer extends EntityStreamer<NaturalQuestions> {

    AtomicLong currentId = new AtomicLong(1);

    public NaturalQuestionStreamer(String jsonDataPath,
                                   String csvDelimiter,
                                   String indicesDelimiter){
        super(jsonDataPath, csvDelimiter, indicesDelimiter);
    }

    @Override
    public List<NaturalQuestions> createEntities(JsonObject dataRow) {
        NaturalQuestions naturalQuestion = new NaturalQuestions();
        naturalQuestion.setQuestionNGrams(getQuestionIndices(dataRow));
        naturalQuestion.setDocumentNGrams(getDocumentIndices(dataRow));
        naturalQuestion.setId(currentId.getAndIncrement());
        return Collections.singletonList(naturalQuestion);
    }

    protected abstract String getQuestionIndices(JsonObject dataRow);

    protected abstract String getDocumentIndices(JsonObject dataRow);

    protected List<String> getDocumentTokens(JsonObject dataRow) {
        List<JsonObject> documentTokenObjects = getAsList(dataRow.get("documentTokens").getAsJsonArray()).stream()
                .map(JsonElement::getAsJsonObject)
                .collect(Collectors.toList());

        return documentTokenObjects.stream().map(jsonObject -> jsonObject.get("token").getAsString()).collect(Collectors.toList());
    }
}
