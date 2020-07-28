package com.braincourt.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class RcvArticlesWithoutTopicTags extends RcvArticles {

    @Id
    public String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int articleId;

    @Lob
    public String queryArticleNGramIndices;

    @Lob
    public String queryArticleWordIndices;

    public RcvArticlesWithoutTopicTags(int articleId,
                                       String articleNGramIndices,
                                       String articleWordIndices) {
        this.articleId = articleId;
        this.queryArticleNGramIndices = articleNGramIndices;
        this.queryArticleWordIndices = articleWordIndices;
    }

    public RcvArticlesWithoutTopicTags() {
    }
}
