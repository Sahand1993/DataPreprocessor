package com.braincourt.preprocessing.dataobjects;

import java.util.List;

public class ConfluenceDataObject extends DataObject {

    private String id;
    private List<String> titleTokens;
    private String webUi;
    private String title;

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

    public String getId() {
        return id;
    }

    public List<String> getTitleTokens() {
        return titleTokens;
    }

    public String getWebUi() {
        return webUi;
    }

    public String getTitle() {
        return title;
    }

    public ConfluenceDataObject setTitle(String title) {
        this.title = title;
        return this;
    }
}
