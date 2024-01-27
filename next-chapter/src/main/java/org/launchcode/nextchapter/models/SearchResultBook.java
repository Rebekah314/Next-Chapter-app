package org.launchcode.nextchapter.models;

import java.util.List;

public class SearchResultBook {
    private String key;
    private String title;
    private int cover_i;
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

    public int getCover_i() {
        return cover_i;
    }

    public void setCover_i(int cover_i) {
        this.cover_i = cover_i;
    }

    public List<String> getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(List<String> author_name) {
        this.author_name = author_name;
    }
}
