package org.launchcode.nextchapter.models;

import java.util.List;

//This class handles all relevant data within the json returned by the OpenLibrary API,
// viewable within "docs" here: https://openlibrary.org/search.json?q=the+lord+of+the+rings

public class SearchResultBook {
    private String key;
    private String title;
    private String cover_i;
    private List<String> author_name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_i() {
        return cover_i;
    }

    public void setCover_i(String cover_i) {
        this.cover_i = cover_i;
    }

    public List<String> getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(List<String> author_name) {
        this.author_name = author_name;
    }
}
