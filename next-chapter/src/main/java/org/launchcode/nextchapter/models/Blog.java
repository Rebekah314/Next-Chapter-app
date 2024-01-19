package org.launchcode.nextchapter.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class Blog {

    @Id
    @GeneratedValue
    private int id;

    private LocalDate time;

//    @ManyToOne
//    private Club club;
//
//    @ManyToOne
//    private User user;
    // need to set up DTO

    @NotBlank(message = "Title cannot be blank.")
    @Size(min = 1, max = 40, message = "Title must be between 1 and 40 characters.")
    private String title;

    private String content;

    public Blog(){
        this.time = LocalDate.now();
    }

    public Blog(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }


    public LocalDate getTime() {
        return time;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
