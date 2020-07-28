package com.braincourt.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class DuplicateQuestions extends DatabaseEntity {

    @Id
    public String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int question1Id;

    public int question2Id;

    @Lob
    public String question1NGrams;

    @Lob
    public String question2NGrams;

    @Lob
    public String question1WordIndices;

    @Lob
    public String question2WordIndices;

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

    public void setQuestion1WordIndices(String question1WordIndices) {
        this.question1WordIndices = question1WordIndices;
    }

    public void setQuestion2WordIndices(String question2WordIndices) {
        this.question2WordIndices = question2WordIndices;
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
