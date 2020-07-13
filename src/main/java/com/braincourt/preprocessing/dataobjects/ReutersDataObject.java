package com.braincourt.preprocessing.dataobjects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ReutersDataObject extends DataObject {

    int articleId;
    List<String> tokens;
    List<String> topicTags;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public List<String> getTopicTags() {
        return topicTags;
    }

    public void setTopicTags(List<String> topicTags) {
        this.topicTags = topicTags;
    }

}
