package org.launchcode.nextchapter.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Club {

    @Id
    @GeneratedValue
    private int id;

    private String displayName;

//    private List<User> members = new ArrayList<>();

    private String activeBook;

    public Club(String displayName, String activeBook) {
        this.displayName = displayName;
        this.activeBook = activeBook;
    }

    public Club() {}

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Club club = (Club) o;
        return id == club.id && Objects.equals(displayName, club.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, displayName);
    }
}
