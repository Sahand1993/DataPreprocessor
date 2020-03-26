package com.braincourt.onehotvectors.writers.entitystream;


import com.braincourt.mysql.entities.NaturalQuestions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class NaturalQuestionStreamer extends EntityStreamer<NaturalQuestions> { // TODO: Think about how we are going to combine this in a clean way with the databasewriters

    int i = 0;

    public NaturalQuestionStreamer(
            @Value("${naturalQuestions.home}") String datasetHome,
            @Value("${filename.json}") String jsonFileName) {
        this.jsonDataPath = datasetHome + jsonFileName;
    }

    @Override
    public Optional<NaturalQuestions> createEntity(JsonObject dataRow) {
        NaturalQuestions naturalQuestions = new NaturalQuestions();
        naturalQuestions.setQuestionNGrams(getQuestionNGrams(dataRow));
        naturalQuestions.setDocumentNGrams(getDocumentNGrams(dataRow));
        return Optional.of(naturalQuestions);
    }

    private String getQuestionNGrams(JsonObject dataRow) {
        List<String> questionTokens = StreamSupport.stream(dataRow.getAsJsonArray("questionTokens").spliterator(), false)
                .map(JsonElement::getAsString)
                .collect(Collectors.toList());

        SortedSet<String> nGramIds = getNGramIds(questionTokens);
        return String.join(",", nGramIds);
    }

    private String getDocumentNGrams(JsonObject dataRow) {
        List<String> documentTokens = StreamSupport.stream(dataRow.getAsJsonArray("documentTokens").spliterator(), false)
                .map(tokenObject -> tokenObject.getAsJsonObject().get("token").getAsString())
                .collect(Collectors.toList());

        SortedSet<String> nGramIds = getNGramIds(documentTokens);
        return String.join(",", nGramIds);
    }
}
