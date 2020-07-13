package com.braincourt.vocabularyextraction.wordstreamers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class ConfluenceWordStreamer extends WordStreamer {

    public ConfluenceWordStreamer(@Value("${processed.data.dir}") String preprocessedDataDir,
                             @Value("${confluence.folder}") String confluenceFolderName,
                             @Value("${filename.json}") String midJsonFileName) {
        this.jsonFilePath = preprocessedDataDir + confluenceFolderName + midJsonFileName;
    }

    @Override
    Stream<String> getWords(JsonObject dataPoint) {
        return StreamSupport.stream(dataPoint.get("titleTokens").getAsJsonArray().spliterator(), false)
                .map(JsonElement::getAsString);
    }
}
