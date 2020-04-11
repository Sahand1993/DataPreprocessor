package com.braincourt.preprocessing.jsonpojos;

import com.braincourt.preprocessing.dataobjects.LongAnswerCandidate;
import com.braincourt.preprocessing.dataobjects.NaturalQuestionsTokenWithHtml;

import java.util.List;

public class NaturalQuestionsJsonPojo {

    String document_title;
    List<NaturalQuestionsTokenWithHtml> document_tokens;
    List<LongAnswerCandidate> long_answer_candidates;
    String question_text;
    List<String> question_tokens;

    public String getDocumentTitle() {
        return document_title;
    }

    public void setDocumentTitle(String document_title) {
        this.document_title = document_title;
    }

    public List<NaturalQuestionsTokenWithHtml> getDocumentTokens() {
        return document_tokens;
    }

    public List<LongAnswerCandidate> getLongAnswerCandidates() {
        return long_answer_candidates;
    }

    public String getQuestionText() {
        return question_text;
    }

    public void setQuestionText(String question_text) {
        this.question_text = question_text;
    }

    public List<String> getQuestionTokens() {
        return question_tokens;
    }

    public void setQuestionTokens(List<String> question_tokens) {
        this.question_tokens = question_tokens;
    }
}
