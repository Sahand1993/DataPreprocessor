package com.braincourt.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class NaturalQuestions extends DatabaseEntity {

    @Id
    public String id;

    public String exampleId;

    @Override
    public String getId() {
        return id;
    }

    public NaturalQuestions setId(String id) {
        this.id = id;
        return this;
    }

    public String titleNGramIndices;

    public String titleWordIndices;

    @Lob
    public String questionNGramIndices;

    @Lob
    public String documentNGramIndices;

    public String questionWordIndices;

    @Lob
    public String documentWordIndices;

    public String getTitleNGramIndices() {
        return titleNGramIndices;
    }

    public NaturalQuestions setTitleNGramIndices(String titleNGramIndices) {
        this.titleNGramIndices = titleNGramIndices;
        return this;
    }

    public String getTitleWordIndices() {
        return titleWordIndices;
    }

    public NaturalQuestions setTitleWordIndices(String titleWordIndices) {
        this.titleWordIndices = titleWordIndices;
        return this;
    }

    public String getQuestionWordIndices() {
        return questionWordIndices;
    }

    public NaturalQuestions setQuestionWordIndices(String questionWordIndices) {
        this.questionWordIndices = questionWordIndices;
        return this;
    }

    public String getDocumentWordIndices() {
        return documentWordIndices;
    }

    public NaturalQuestions setDocumentWordIndices(String documentWordIndices) {
        this.documentWordIndices = documentWordIndices;
        return this;
    }

    public String getQuestionNGramIndices() {
        return questionNGramIndices;
    }

    public NaturalQuestions setQuestionNGramIndices(String questionNGramIndices) {
        this.questionNGramIndices = questionNGramIndices;
        return this;
    }

    public String getDocumentNGramIndices() {
        return documentNGramIndices;
    }

    public NaturalQuestions setDocumentNGramIndices(String documentNGramIndices) {
        this.documentNGramIndices = documentNGramIndices;
        return this;
    }

    public NaturalQuestions setExampleId(String exampleId) {
        this.exampleId = exampleId;
        return this;
    }
}
