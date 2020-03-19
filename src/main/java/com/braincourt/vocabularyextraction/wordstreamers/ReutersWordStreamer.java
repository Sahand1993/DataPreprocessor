package com.braincourt.vocabularyextraction.wordstreamers;

import com.braincourt.vocabularyextraction.VocabularyExtractor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ReutersWordStreamer extends WordStreamer {

    public ReutersWordStreamer() {
        this.jsonFilePath = VocabularyExtractor.preprocessedDataDir + "/rcv1/data.json";
    }

    @Override
    Stream<String> getWords(JsonObject dataPoint) {
        return StreamSupport.stream(dataPoint.get("tokens").getAsJsonArray().spliterator(), true)
                .map(JsonElement::getAsString);
    }
}
