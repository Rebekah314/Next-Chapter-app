package org.launchcode.nextchapter.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class ActiveBook {

    @Id
    @GeneratedValue
    private int id;

    private String key;
    private String title;
    private String coverId;
    private List<String> author_name;

    public int getId() {
        return id;
    }

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

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String cover_i) {
        this.coverId = coverId;
    }

    public List<String> getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(List<String> author_name) {
        this.author_name = author_name;
    }
}
