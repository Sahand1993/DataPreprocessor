package com.braincourt.vocabularyextraction.wordstreamers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class NaturalQuestionsWordStreamer extends WordStreamer {

    public NaturalQuestionsWordStreamer(@Value("${naturalQuestions.home}") String preprocessedDataDir) {
        this.jsonFilePath = preprocessedDataDir + "/data.json";
    }

    @Override
    Stream<String> getWords(JsonObject dataPoint) {
        return Stream.concat(getQuestionTokens(dataPoint), getDocumentTokens(dataPoint));
    }

    private Stream<String> getQuestionTokens(JsonObject dataPoint) {
        return StreamSupport.stream(dataPoint.get("questionTokens").getAsJsonArray().spliterator(), true)
                .map(JsonElement::getAsString);
    }

    private Stream<String> getDocumentTokens(JsonObject dataPoint) {
        return StreamSupport.stream(dataPoint.get("documentTokens").getAsJsonArray().spliterator(), true)
                .map(jsonElement -> jsonElement.getAsJsonObject().get("token").getAsString());
    }

}
