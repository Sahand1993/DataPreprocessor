package com.braincourt.preprocessing.dataobjects;

import java.util.List;

public class QuoraQuestionPairDataObject extends DataObject {

    int id;
    int question1Id;
    int question2Id;
    List<String> question1_tokens;
    List<String> question2_tokens;
    Boolean isDuplicate;

    public QuoraQuestionPairDataObject(int id,
                                       int question1Id,
                                       int question2Id,
                                       List<String> question1Tokens,
                                       List<String> question2Tokens,
                                       Boolean isDuplicate) {
        this.id = id;
        this.question1Id = question1Id;
        this.question2Id = question2Id;
        this.question1_tokens = question1Tokens;
        this.question2_tokens = question2Tokens;
        this.isDuplicate = isDuplicate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion1Id() {
        return question1Id;
    }

    public void setQuestion1Id(int question1Id) {
        this.question1Id = question1Id;
    }

    public int getQuestion2Id() {
        return question2Id;
    }

    public void setQuestion2Id(int question2Id) {
        this.question2Id = question2Id;
    }

    public List<String> getQuestion1Tokens() {
        return question1_tokens;
    }

    public void setQuestion1Tokens(List<String> question1_tokens) {
        this.question1_tokens = question1_tokens;
    }

    public List<String> getQuestion2Tokens() {
        return question2_tokens;
    }

    public void setQuestion2Tokens(List<String> question2_tokens) {
        this.question2_tokens = question2_tokens;
    }

    public Boolean getDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(Boolean duplicate) {
        isDuplicate = duplicate;
    }

}
