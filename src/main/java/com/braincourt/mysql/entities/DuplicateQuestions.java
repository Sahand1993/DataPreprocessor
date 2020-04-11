package com.braincourt.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class DuplicateQuestions extends DatabaseEntity {

    public int question1Id;

    public int question2Id;

    @Lob
    public String question1NGrams;

    @Lob
    public String question2NGrams;

    public Boolean isDuplicate;


    public String getQuestion1NGrams() {
        return question1NGrams;
    }

    public void setQuestion1NGrams(String question1NGrams) {
        this.question1NGrams = question1NGrams;
    }

    public String getQuestion2NGrams() {
        return question2NGrams;
    }

    public void setQuestion2NGrams(String question2NGrams) {
        this.question2NGrams = question2NGrams;
    }

    public Boolean getIsDuplicate() {
        return isDuplicate;
    }

    public void setIsDuplicate(Boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    public void setQuestion1Id(int question1Id) {
        this.question1Id = question1Id;
    }

    public void setQuestion2Id(int question2Id) {
        this.question2Id = question2Id;
    }
}
