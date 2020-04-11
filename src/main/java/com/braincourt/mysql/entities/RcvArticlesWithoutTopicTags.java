package com.braincourt.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class RcvArticlesWithoutTopicTags extends RcvArticles {

    public int articleId;

    @Lob
    public String queryArticleIndices; // Can be either ngram och vocabulary indices

    public RcvArticlesWithoutTopicTags(int articleId,
                                       String articleIndices) {
        this.articleId = articleId;
        this.queryArticleIndices = articleIndices;
    }

    public RcvArticlesWithoutTopicTags() {
    }
}
