package com.braincourt.preprocessing.dataobjects;

public class NaturalQuestionsToken {
    int start_byte;
    int end_byte;
    String token;

    public NaturalQuestionsToken(int start_byte, int end_byte, String token) {
        this.start_byte = start_byte;
        this.end_byte = end_byte;
        this.token = token;
    }

    public int getStartByte() {
        return start_byte;
    }

    public int getEndByte() {
        return end_byte;
    }

    public String getToken() {
        return token;
    }

    public NaturalQuestionsToken setToken(String token) {
        this.token = token;
        return this;
    }
}
