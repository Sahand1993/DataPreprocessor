package com.braincourt.mysql.entities;

public class SquadEntity extends DatabaseEntity {

    public String id;

    public String questionId;

    public String titleId;

    public String questionNGramIndices;

    public String questionWordIndices;

    public String titleNGramIndices;

    public String titleWordIndices;

    public SquadEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getQuestionId() {
        return questionId;
    }

    public SquadEntity setQuestionId(String questionId) {
        this.questionId = questionId;
        return this;
    }

    public String getTitleId() {
        return titleId;
    }

    public SquadEntity setTitleId(String titleId) {
        this.titleId = titleId;
        return this;
    }

    public String getQuestionNGramIndices() {
        return questionNGramIndices;
    }

    public SquadEntity setQuestionNGramIndices(String questionNGramIndices) {
        this.questionNGramIndices = questionNGramIndices;
        return this;
    }

    public String getQuestionWordIndices() {
        return questionWordIndices;
    }

    public SquadEntity setQuestionWordIndices(String questionWordIndices) {
        this.questionWordIndices = questionWordIndices;
        return this;
    }

    public String getTitleNGramIndices() {
        return titleNGramIndices;
    }

    public SquadEntity setTitleNGramIndices(String titleNGramIndices) {
        this.titleNGramIndices = titleNGramIndices;
        return this;
    }

    public String getTitleWordIndices() {
        return titleWordIndices;
    }

    public SquadEntity setTitleWordIndices(String titleWordIndices) {
        this.titleWordIndices = titleWordIndices;
        return this;
    }

    @Override
    public String getId() {
        return id;
    }
}
