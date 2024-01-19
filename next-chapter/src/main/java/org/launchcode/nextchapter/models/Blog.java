package org.launchcode.nextchapter.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


import java.time.LocalDateTime;

@Entity
public class Blog {

    @Id
    @GeneratedValue
    private int id;

    private LocalDateTime time;

//    @ManyToOne
//    private Club club;
//
//    @ManyToOne
//    private User user;
    // need to set up DTO

    @NotBlank(message = "Title cannot be blank.")
    @Size(min = 1, max = 40, message = "Title must be between 1 and 40 characters.")
    private String title;

    private String bookContext;

    public String getBookContext() {
        return bookContext;
    }

    public void setBookContext(String bookContext) {
        this.bookContext = bookContext;
    }

    private String content;

    public Blog(){
        this.time = LocalDateTime.now();
    }

    public Blog(String title, String bookContext, String content) {
        this();
        this.title = title;
        this.bookContext = bookContext;
        this.content = content;
    }

    public int getId() {
        return id;
    }


    public LocalDateTime getTime() {
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
