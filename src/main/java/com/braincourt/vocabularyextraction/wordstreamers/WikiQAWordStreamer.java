package com.braincourt.vocabularyextraction.wordstreamers;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class WikiQAWordStreamer extends WordStreamer {
    public WikiQAWordStreamer(@Value("${processed.data.dir}") String preprocessedDataDir,
                              @Value("${wikiQA.folder}") String wikiQAFolder,
                              @Value("${filename.json}") String midJsonFileName) {
        this.jsonFilePath = preprocessedDataDir + wikiQAFolder + midJsonFileName;
    }

    @Override
    Stream<String> getWords(JsonObject dataPoint) {
        return Stream.concat(getQuestionTokens(dataPoint), getTitleTokens(dataPoint));
    }
}
