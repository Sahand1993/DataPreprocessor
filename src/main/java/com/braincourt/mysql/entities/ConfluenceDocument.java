package com.braincourt.mysql.entities;

public class ConfluenceDocument extends DatabaseEntity {

    public String id;

    public String titleNGrams;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitleNGrams(String titleNGrams) {
        this.titleNGrams = titleNGrams;
    }

}
