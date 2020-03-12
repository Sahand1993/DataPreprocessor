package com.braincourt.preprocessing.dataobjects;

import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.dataobjects.LongAnswerCandidate;
import com.braincourt.preprocessing.dataobjects.NaturalQuestionsToken;
import com.google.gson.Gson;

import java.util.List;

public class NaturalQuestionsDataObject extends DataObject {

    private List<NaturalQuestionsToken> documentTokens;
    private String documentTitle;
    private List<String> questionTokens;
    private List<LongAnswerCandidate> longAnswerCandidates;

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

    public void setQuestionTokens(List<String> tokens) {
        this.questionTokens = tokens;
    }

    @Override
    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void setLongAnswerCandidates(List<LongAnswerCandidate> longAnswerCandidates) {
        this.longAnswerCandidates = longAnswerCandidates;
    }
}
