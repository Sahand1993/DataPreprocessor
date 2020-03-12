package com.braincourt.preprocessing.dataobjects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ReutersDataObject extends DataObject {

    String articleId;
    List<String> tokens;
    List<String> topicTags;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
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

    @Override
    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("article_id", getArticleId());
        jsonObject.put("tokens", new JSONArray(getTokens()));
        jsonObject.put("topic_tags", new JSONArray(getTopicTags()));
        return jsonObject.toString();
    }
}
