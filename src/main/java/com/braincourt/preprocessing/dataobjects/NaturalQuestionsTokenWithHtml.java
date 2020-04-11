package com.braincourt.preprocessing.dataobjects;

public class NaturalQuestionsTokenWithHtml extends NaturalQuestionsToken {

    Boolean html_token;

    public NaturalQuestionsTokenWithHtml(int start_byte, int end_byte, String token) {
        super(start_byte, end_byte, token);
    }

    public Boolean isHtmlToken() {
        return html_token;
    }
}
