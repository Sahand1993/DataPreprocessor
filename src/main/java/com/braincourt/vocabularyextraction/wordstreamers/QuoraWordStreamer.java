package com.braincourt.vocabularyextraction.wordstreamers;

import com.braincourt.vocabularyextraction.VocabularyExtractor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class QuoraWordStreamer extends WordStreamer {

    public QuoraWordStreamer() {
        this.jsonFilePath = VocabularyExtractor.preprocessedDataDir + "/quora/data.json";
    }

    @Override
    Stream<String> getWords(JsonObject dataPoint) {
        return Stream.concat(getQuestion1Tokens(dataPoint), getQuestion2Tokens(dataPoint));
    }

    private Stream<String> getQuestion1Tokens(JsonObject dataPoint) {
        return StreamSupport.stream(dataPoint.get("question1_tokens").getAsJsonArray().spliterator(), true)
                .map(JsonElement::getAsString);
    }

    private Stream<String> getQuestion2Tokens(JsonObject dataPoint) {
        return StreamSupport.stream(dataPoint.get("question2_tokens").getAsJsonArray().spliterator(), true)
                .map(JsonElement::getAsString);
    }
}
