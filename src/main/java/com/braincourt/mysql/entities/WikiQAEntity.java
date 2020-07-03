package com.braincourt.mysql.entities;

public class WikiQAEntity extends DatabaseEntity {
    public Long id;

    public String questionId;

    public String docId;

    public String questionNGramIndices;

    public String questionWordIndices;

    public String titleNGramIndices;

    public String titleWordIndices;

    @Override
    public Long getId() {
        return id;
    }

    public WikiQAEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getQuestionId() {
        return questionId;
    }

    public WikiQAEntity setQuestionId(String questionId) {
        this.questionId = questionId;
        return this;
    }

    public String getQuestionNGramIndices() {
        return questionNGramIndices;
    }

    public WikiQAEntity setQuestionNGramIndices(String questionNGramIndices) {
        this.questionNGramIndices = questionNGramIndices;
        return this;
    }

    public String getQuestionWordIndices() {
        return questionWordIndices;
    }

    public WikiQAEntity setQuestionWordIndices(String questionWordIndices) {
        this.questionWordIndices = questionWordIndices;
        return this;
    }

    public String getTitleNGramIndices() {
        return titleNGramIndices;
    }

    public WikiQAEntity setTitleNGramIndices(String titleNGramIndices) {
        this.titleNGramIndices = titleNGramIndices;
        return this;
    }

    public String getTitleWordIndices() {
        return titleWordIndices;
    }

    public WikiQAEntity setTitleWordIndices(String titleWordIndices) {
        this.titleWordIndices = titleWordIndices;
        return this;
    }

    public WikiQAEntity setDocId(String docId) {
        this.docId = docId;
        return this;
    }
}
