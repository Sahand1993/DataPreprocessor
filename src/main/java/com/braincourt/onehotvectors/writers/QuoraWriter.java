package com.braincourt.onehotvectors.writers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;


public class QuoraWriter extends Writer {

    public QuoraWriter(String preprocessed_data_path, String preprocessed_onehot_path, int N) {
        super(preprocessed_data_path, preprocessed_onehot_path, N);
    }

    @Override
    protected void writeHeaderCsvRow() {
        try {
            bufferedWriter.write("id;question1_ngrams_sparse;question2_ngrams_sparse;is_duplicate");
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a dataRow object containing the one-hot representation of dataRow.
     * @param dataRow
     * @return
     */
    @Override
    public String createOneHotCsv(JsonObject dataRow) {
        String result;
        result = dataRow.get("id").getAsString() + ";";
        result = result + getNGramCsv(dataRow) + ";";
        result = result + (dataRow.get("isDuplicate").getAsBoolean() ? 1 : 0);
        return result;
    }

    private String getNGramCsv(JsonObject json) {
        List<String> question1Tokens = getAsList(json.getAsJsonArray("question1_tokens")).stream()
                .map(JsonElement::getAsString) // TODO: Extract to method
                .collect(Collectors.toList());
        List<String> question2Tokens = getAsList(json.getAsJsonArray("question2_tokens")).stream()
                .map(JsonElement::getAsString)
                .collect(Collectors.toList());

        SortedSet<String> question1NGramBag = getNGramIds(question1Tokens);
        SortedSet<String> question2NGramBag = getNGramIds(question2Tokens);

        return String.join(",", question1NGramBag) + ";" + String.join(",", question2NGramBag);
    }




}
