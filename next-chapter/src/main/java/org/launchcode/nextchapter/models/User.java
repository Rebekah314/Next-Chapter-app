package org.launchcode.nextchapter.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User extends AbstractEntity{

    private String username;

    private String pwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//    @ManyToMany(mappedBy = "members")
//    private final List<Club> clubs = new ArrayList<>();
    //need to set up DTO, chapter 18.5

    public User(String username, String displayName, String password) {
        this.username = username;
        this.setDisplayName(displayName);
        this.pwHash = encoder.encode(password);
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

//    public List<Club> getClubs() {
//        return clubs;
//    }
//
//    public void setClubs(List<Club> clubs) {
//        this.clubs = clubs;
//    }

}
