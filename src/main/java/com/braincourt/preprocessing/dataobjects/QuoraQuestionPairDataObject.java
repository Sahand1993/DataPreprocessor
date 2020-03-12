package com.braincourt.preprocessing.dataobjects;

import com.google.gson.Gson;

import java.util.List;

public class QuoraQuestionPairDataObject extends DataObject {

    int id;
    int question1Id;
    int question2Id;
    List<String> question1;
    List<String> question2;
    Boolean isDuplicate;

    public QuoraQuestionPairDataObject(int id,
                                       int question1Id,
                                       int question2Id,
                                       List<String> question1,
                                       List<String> question2,
                                       Boolean isDuplicate) {
        this.id = id;
        this.question1Id = question1Id;
        this.question2Id = question2Id;
        this.question1 = question1;
        this.question2 = question2;
        this.isDuplicate = isDuplicate;
    }

    @Override
    public String toJsonString() {
        return new Gson().toJson(this);
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

    public List<String> getQuestion1() {
        return question1;
    }

    public void setQuestion1(List<String> question1) {
        this.question1 = question1;
    }

    public List<String> getQuestion2() {
        return question2;
    }

    public void setQuestion2(List<String> question2) {
        this.question2 = question2;
    }

    public Boolean getDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(Boolean duplicate) {
        isDuplicate = duplicate;
    }
}
