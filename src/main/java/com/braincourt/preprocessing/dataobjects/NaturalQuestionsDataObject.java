package com.braincourt.preprocessing.dataobjects;

import com.google.gson.Gson;

import java.util.List;

public class NaturalQuestionsDataObject extends DataObject {

    private List<NaturalQuestionsToken> documentTokens;
    private String documentTitle;
    private List<String> questionTokens;
 //   private List<LongAnswerCandidate> longAnswerCandidates;
    private int id;

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public List<NaturalQuestionsToken> getDocumentTokens() {
        return documentTokens;
    }

    public void setDocumentTokens(List<NaturalQuestionsToken> tokens) {
        this.documentTokens = tokens;
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

 //   public void setLongAnswerCandidates(List<LongAnswerCandidate> longAnswerCandidates) {
 //       this.longAnswerCandidates = longAnswerCandidates;
 //   }
}
