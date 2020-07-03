package com.braincourt.vocabularyextraction.wordstreamers;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class NaturalQuestionsWordStreamer extends WordStreamer {
    protected Stream<String> getQuestionTokens(JsonObject dataPoint) {
        return StreamSupport.stream(dataPoint.get("questionTokens").getAsJsonArray().spliterator(), true)
                .map(JsonElement::getAsString);
    }

}
