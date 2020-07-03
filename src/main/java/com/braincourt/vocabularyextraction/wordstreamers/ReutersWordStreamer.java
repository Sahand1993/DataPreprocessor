package com.braincourt.vocabularyextraction.wordstreamers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class ReutersWordStreamer extends WordStreamer {

    public ReutersWordStreamer(@Value("${reuters.folder}") String reutersFolderName,
                               @Value("${processed.data.dir}") String preprocessedDataDir,
                               @Value("${filename.json}") String midJsonFileName) {
        this.jsonFilePath = preprocessedDataDir + reutersFolderName + midJsonFileName;
    }

    @Override
    Stream<String> getWords(JsonObject dataPoint) {
        return StreamSupport.stream(dataPoint.get("tokens").getAsJsonArray().spliterator(), true)
                .map(JsonElement::getAsString);
    }
}
