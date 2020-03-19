package com.braincourt.onehotvectors.writers;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ReutersWriter extends Writer {

    public ReutersWriter(String preprocessed_data_path, String preprocessed_onehot_path, int N) {
        super(preprocessed_data_path, preprocessed_onehot_path, N);
    }

    @Override
    protected void writeHeaderCsvRow() {
        try {
            bufferedWriter.write("article_id;article_ngrams_sparse;topic_tags");
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createOneHotCsv(JsonObject dataRow) {
        String result = "";
        result += dataRow.get("article_id").getAsString() + ";";
        result += getNGramJsonObject(dataRow) + ";";
        result += StreamSupport.stream(dataRow.get("topic_tags").getAsJsonArray().spliterator(), false)
                .map(JsonElement::getAsString)
                .collect(Collectors.joining(","));
        return result;
    }

    private String getNGramJsonObject(JsonObject dataRow) {
        List<String> articleTokens = StreamSupport.stream(dataRow.getAsJsonArray("tokens").spliterator(), false)
                .map(JsonElement::getAsString)
                .collect(Collectors.toList());

        SortedSet<String> nGramIds = getNGramIds(articleTokens);

        return String.join(",", nGramIds);
    }
}
