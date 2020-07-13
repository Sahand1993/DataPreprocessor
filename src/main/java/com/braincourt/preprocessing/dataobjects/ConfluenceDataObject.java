package com.braincourt.preprocessing.dataobjects;

import java.util.List;

public class ConfluenceDataObject extends DataObject {

    private String id;
    private List<String> titleTokens;
    private String webUi;

    public ConfluenceDataObject setId(String id) {
        this.id = id;
        return this;
    }

    public ConfluenceDataObject setTitleTokens(List<String> titleTokens) {
        this.titleTokens = titleTokens;
        return this;
    }

    public ConfluenceDataObject setWebUi(String webUi) {
        this.webUi = webUi;
        return this;
    }
}
