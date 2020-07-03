package com.braincourt.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class RcvArticlesWithoutTopicTags extends RcvArticles {

    @Id
    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
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
