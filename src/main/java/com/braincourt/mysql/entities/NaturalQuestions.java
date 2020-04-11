package com.braincourt.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class NaturalQuestions extends DatabaseEntity {

    @Lob
    public String questionNGrams;

    @Lob
    public String documentNGrams;

    public String getQuestionNGrams() {
        return questionNGrams;
    }

    public void setQuestionNGrams(String questionNGrams) {
        this.questionNGrams = questionNGrams;
    }

    public String getDocumentNGrams() {
        return documentNGrams;
    }

    public void setDocumentNGrams(String documentNGrams) {
        this.documentNGrams = documentNGrams;
    }
}
