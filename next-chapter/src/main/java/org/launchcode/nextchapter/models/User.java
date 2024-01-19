package org.launchcode.nextchapter.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User extends AbstractEntity{

    private String username;

//    @ManyToMany(mappedBy = "members")
//    private final List<Club> clubs = new ArrayList<>();
    //need to set up DTO, chapter 18.5

    public User(String username, String displayName) {
        this.username = username;
        this.setDisplayName(displayName);
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public List<Club> getClubs() {
//        return clubs;
//    }
//
//    public void setClubs(List<Club> clubs) {
//        this.clubs = clubs;
//    }

}
