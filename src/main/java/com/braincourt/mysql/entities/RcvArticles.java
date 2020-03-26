package com.braincourt.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class RcvArticles extends DatabaseEntity {

    @Lob
    private String articleNGrams;

    private String tags;

    public String getArticleNGrams() {
        return articleNGrams;
    }

    public void setArticleNGrams(String articleNGrams) {
        this.articleNGrams = articleNGrams;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
