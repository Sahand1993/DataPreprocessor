package com.braincourt.mysql.entities;

public class ConfluenceDocument extends DatabaseEntity {

    public long id;

    public String titleNGrams;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitleNGrams() {
        return titleNGrams;
    }

    public void setTitleNGrams(String titleNGrams) {
        this.titleNGrams = titleNGrams;
    }
}
