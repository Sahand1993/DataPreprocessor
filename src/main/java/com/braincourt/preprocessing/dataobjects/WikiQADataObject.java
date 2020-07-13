package com.braincourt.preprocessing.dataobjects;

import java.util.List;

public class WikiQADataObject extends DataObject {

    public String questionId;
    public List<String> questionTokens;
    public String docId;
    public List<String> titleTokens;
    private boolean answered;

    public String getQuestionId() {
        return questionId;
    }

    public List<String> getQuestionTokens() {
        return questionTokens;
    }

    public String getDocId() {
        return docId;
    }

    public List<String> getTitleTokens() {
        return titleTokens;
    }

    public WikiQADataObject setQuestionId(String questionId) {
        this.questionId = questionId;
        return this;
    }

    public WikiQADataObject setQuestionTokens(List<String> questionTokens) {
        this.questionTokens = questionTokens;
        return this;
    }

    public WikiQADataObject setDocId(String docId) {
        this.docId = docId;
        return this;
    }

    public WikiQADataObject setTitleTokens(List<String> titleTokens) {
        this.titleTokens = titleTokens;
        return this;
    }

    public DataObject setAnswered(boolean answered) {
        this.answered = answered;
        return this;
    }

}
