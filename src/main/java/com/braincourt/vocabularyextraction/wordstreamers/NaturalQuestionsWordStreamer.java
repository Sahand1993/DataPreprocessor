package com.braincourt.vocabularyextraction.wordstreamers;

import com.braincourt.vocabularyextraction.VocabularyExtractor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class NaturalQuestionsWordStreamer extends WordStreamer {

    public NaturalQuestionsWordStreamer() {
        this.jsonFilePath = VocabularyExtractor.preprocessedDataDir + "/nq/data.json";
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
