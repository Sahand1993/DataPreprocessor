package com.braincourt.onehotvectors.writers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * Responsible for writing onehot representations to the right file on disk
 */
public abstract class Writer {

    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    Map<String, Map<String, Integer>> wordToNGrams;
    String preprocessed_data_path;
    String preprocessed_onehot_path;
    int N;

    public Writer(String preprocessed_data_path_json, String preprocessed_data_path, int N) {
        this.preprocessed_data_path = preprocessed_data_path_json;
        this.preprocessed_onehot_path = preprocessed_data_path + String.format("onehot_%sgram.csv", N);
        this.N = N;
    }

    public void write(Map<String, Map<String, Integer>> wordToNGrams) {
        this.wordToNGrams = wordToNGrams;
        try {

            System.out.println("Writing one-hot representations to " + preprocessed_onehot_path);
            bufferedReader = new BufferedReader(new FileReader(preprocessed_data_path));
            bufferedWriter = new BufferedWriter(new FileWriter(preprocessed_onehot_path));
            writeHeaderCsvRow();
            bufferedReader.lines().map(JsonParser::parseString)
                    .map(JsonElement::getAsJsonObject)
                    .map(this::createOneHotCsv)
                    .forEach(this::write);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    protected abstract void writeHeaderCsvRow();

    public abstract String createOneHotCsv(JsonObject dataRow);

    public void write(String csv) {
        try {
            bufferedWriter.write(csv);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    SortedSet<String> getNGramIds(List<String> tokens) {
        SortedSet<String> nGramIds = new TreeSet<>();

        for (int i = 0; i < tokens.size(); i++) {

            String token = tokens.get(i);
            Collection<Integer> tokenNGramIds = wordToNGrams.get(token).values();
            tokenNGramIds.stream()
                    .map(Object::toString)
                    .forEach(nGramIds::add);

        }

        return nGramIds;
    }

    List<JsonElement> getAsList(JsonArray json) {
        return StreamSupport.stream(json.spliterator(), false).collect(Collectors.toList());
    }
}
