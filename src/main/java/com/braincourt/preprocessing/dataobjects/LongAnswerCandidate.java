package com.braincourt.preprocessing.dataobjects;

public class LongAnswerCandidate {

    int start_byte;
    int end_byte;
    int start_token;
    int end_token;
    Boolean top_level;


    public int getStartToken() {
        return start_token;
    }

    public int getEndToken() {
        return end_token;
    }

    public Boolean getTopLevel() {
        return top_level;
    }

    public int getStartByte() {
        return start_byte;
    }

    public int getEndByte() {
        return end_byte;
    }

}
