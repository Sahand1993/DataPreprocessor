package com.braincourt.vocabularyextraction.wordstreamers;

import com.braincourt.onehotvectors.entitystreamers.NaturalQuestionAllStreamer;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class NaturalQuestionsDocumentWordStreamer extends NaturalQuestionsWordStreamer {

    public NaturalQuestionsDocumentWordStreamer(@Value("${processed.data.dir}") String preprocessedDataDir,
                                                @Value("${nq.folder}") String nqDirName,
                                                @Value("${filename.json}") String midJsonFileName) {
        this.jsonFilePath = preprocessedDataDir + nqDirName + midJsonFileName;
    }

    @Override
    Stream<String> getWords(JsonObject dataPoint) {
        return Stream.concat(getQuestionTokens(dataPoint), getDocumentTokens(dataPoint));
    }


    private Stream<String> getDocumentTokens(JsonObject dataPoint) {
        return NaturalQuestionAllStreamer.getDocumentTokens(dataPoint).stream();
//        return StreamSupport.stream(dataPoint.get("documentTokens").getAsJsonArray().spliterator(), true)
//                .map(jsonElement -> jsonElement.getAsJsonObject().get("token").getAsString());
    }

}
