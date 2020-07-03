package com.braincourt.vocabularyextraction.wordstreamers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class NaturalQuestionsTitleWordStreamer extends NaturalQuestionsWordStreamer {

    public NaturalQuestionsTitleWordStreamer(@Value("${processed.data.dir}") String preprocessedDataDir,
                                                @Value("${nq.folder}") String nqDirName,
                                                @Value("${filename.json}") String midJsonFileName) {
        this.jsonFilePath = preprocessedDataDir + nqDirName + midJsonFileName;
    }
    @Override
    Stream<String> getWords(JsonObject dataPoint) {
        return Stream.concat(getQuestionTokens(dataPoint), getNaturalQuestionsTitleTokens(dataPoint));
    }

    private Stream<String> getNaturalQuestionsTitleTokens(JsonObject dataPoint) {
        return StreamSupport.stream(dataPoint.get("titleTokens").getAsJsonArray().spliterator(), false)
                .map(JsonElement::getAsString);
    }
}
