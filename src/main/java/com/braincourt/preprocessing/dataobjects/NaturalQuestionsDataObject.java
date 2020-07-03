package com.braincourt.preprocessing.dataobjects;

import com.google.gson.Gson;

import java.util.List;

public class NaturalQuestionsDataObject extends DataObject {

    private List<String> titleTokens;
    private List<String> questionTokens;
    private int id;
    private long exampleId;
    private List<String> documentTokens;

    public List<String> getTitleTokens() {
        return titleTokens;
    }

    public void setTitleTokens(List<String> titleTokens) {
        this.titleTokens = titleTokens;
    }

    public List<String> getQuestionTokens() {
        return questionTokens;
    }

    public void setQuestionTokens(List<String> tokens) {
        this.questionTokens = tokens;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void setExampleId(long example_id) {
        exampleId = example_id;
    }

    public void setDocumentTokens(List<String> documentTokens) {
        this.documentTokens = documentTokens;
    }
}
