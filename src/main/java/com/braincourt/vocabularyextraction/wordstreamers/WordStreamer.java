package com.braincourt.vocabularyextraction.wordstreamers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Stream;

public abstract class WordStreamer {

    /**
     * Assumes that this file has one "wordstreamable" JSON data element at each line.
     */
    String jsonFilePath;

    public Stream<String> getWordStream() {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(jsonFilePath));
            return bufferedReader.lines()
                    .map(this::toJsonObject)
                    .flatMap(this::getWords);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    private JsonObject toJsonObject(String line) {
        return (JsonObject) JsonParser.parseString(line);
    }

    /**
     * Takes in a JSONObject data element containing text that should be returned as a Stream.
     * @return a stream of all words in the data element
     */
    abstract Stream<String> getWords(JsonObject dataPoint);
}
