package com.braincourt.onehotvectors.writers;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class NaturalQuestionsWriter extends Writer {

    int i = 0;

    public NaturalQuestionsWriter(String preprocessed_data_path, String preprocessed_onehot_path, int N) {
        super(preprocessed_data_path, preprocessed_onehot_path, N);
    }

    @Override
    protected void writeHeaderCsvRow() {
        try {
            bufferedWriter.write(String.format("question_%dgrams;article_%dgrams", N, N));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createOneHotCsv(JsonObject dataRow) {
        String result = "";
        result += getQuestionNGrams(dataRow) + ";";
        result += getDocumentNGrams(dataRow);
        return result;
    }



    private String getQuestionNGrams(JsonObject dataRow) {
        List<String> questionTokens = StreamSupport.stream(dataRow.getAsJsonArray("questionTokens").spliterator(), false)
                .map(JsonElement::getAsString)
                .collect(Collectors.toList()); // TODO: Extract to method

        SortedSet<String> nGramIds = getNGramIds(questionTokens);
        if (nGramIds.size() == 0 || questionTokens.size() == 0) {
            System.out.println("got a hit");
            System.out.println(dataRow.toString());
            System.out.println(String.join(" ", questionTokens));
        }
        return String.join(",", nGramIds);
    }

    private String getDocumentNGrams(JsonObject dataRow) {
        List<String> documentTokens = StreamSupport.stream(dataRow.getAsJsonArray("documentTokens").spliterator(), false)
                .map(tokenObject -> tokenObject.getAsJsonObject().get("token").getAsString())
                .collect(Collectors.toList());

        SortedSet<String> nGramIds = getNGramIds(documentTokens);
        return String.join(",", nGramIds);
    }
}
