package com.braincourt.preprocessing.dataobjects;

public class NaturalQuestionsToken {

    int start_byte;
    int end_byte;
    Boolean html_token; // TODO: Consider removing
    String token;

    public int getStartByte() {
        return start_byte;
    }

    public int getEndByte() {
        return end_byte;
    }

    public Boolean isHtmlToken() {
        return html_token;
    }

    public String getToken() {
        return token;
    }

    public NaturalQuestionsToken setToken(String token) {
        this.token = token;
        return this;
    }
}
