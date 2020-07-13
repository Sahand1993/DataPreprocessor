package com.braincourt.preprocessing.dataobjects;

import java.util.List;

public class SquadDataObject extends DataObject {

    List<String> questionTokens;
    List<String> titleTokens;
    String questionId;
    long titleId;

    public SquadDataObject(String questionId, long titleId, List<String> questionTokens, List<String> titleTokens) {
        this.questionTokens = questionTokens;
        this.titleTokens = titleTokens;
        this.questionId = questionId;
        this.titleId = titleId;
    }

    public List<String> getQuestionTokens() {
        return questionTokens;
    }

    public void setQuestionTokens(List<String> questionTokens) {
        this.questionTokens = questionTokens;
    }

    public List<String> getTitleTokens() {
        return titleTokens;
    }

    public void setTitleTokens(List<String> titleTokens) {
        this.titleTokens = titleTokens;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public long getTitleId() {
        return titleId;
    }

    public void setTitleId(long titleId) {
        this.titleId = titleId;
    }


}
