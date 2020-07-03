package com.braincourt.vocabularyextraction.wordstreamers;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class SquadWordStreamer extends WordStreamer {

    public SquadWordStreamer(@Value("${processed.data.dir}") String preprocessedDataDir,
                             @Value("${squad.folder}") String squadFolder,
                             @Value("${filename.json}") String midJsonFileName) {
        this.jsonFilePath = preprocessedDataDir + squadFolder + midJsonFileName;
    }

    @Override
    Stream<String> getWords(JsonObject dataPoint) {
        return Stream.concat(getQuestionTokens(dataPoint), getTitleTokens(dataPoint));
    }
}
