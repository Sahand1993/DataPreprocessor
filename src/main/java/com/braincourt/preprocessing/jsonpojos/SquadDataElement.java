package com.braincourt.preprocessing.jsonpojos;

import java.util.List;

public class SquadDataElement {

    String title;
    List<SquadParagraph> paragraphs;

    public String getTitle() {
        return title;
    }

    public List<SquadParagraph> getParagraphs() {
        return paragraphs;
    }
}
