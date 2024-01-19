package org.launchcode.nextchapter.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Club extends AbstractEntity {

//    @ManyToMany
//    private final List<User> members = new ArrayList<>();
    //need to set up DTO, chapter 18.5

    private String activeBook;

    public Club(String displayName, String activeBook) {
        this.setDisplayName(displayName);
        this.activeBook = activeBook;
    }

    public Club() {}

//    public List<User> getMembers() {
//        return members;
//    }

//    public void setMembers(List<User> members) {
//        this.members = members;
//    }

    public String getActiveBook() {
        return activeBook;
    }

    public void setActiveBook(String activeBook) {
        this.activeBook = activeBook;
    }

}
