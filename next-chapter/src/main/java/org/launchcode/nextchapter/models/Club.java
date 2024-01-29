package org.launchcode.nextchapter.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Club extends AbstractEntity {

   @ManyToMany
    private List<User> members = new ArrayList<>();
    //need to set up DTO, chapter 18.5

    private String activeBook;

    private String adminPwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Club(String displayName, String activeBook) {
        this.setDisplayName(displayName);
        this.activeBook = activeBook;
        //this.adminPwHash = encoder.encode(adminPw);
    }

    public Club() {}

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
       this.members = members;
    }

    public String getActiveBook() {
        return activeBook;
    }

    public void setActiveBook(String activeBook) {
        this.activeBook = activeBook;
    }

    public String getPwHash() {
        return adminPwHash;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, adminPwHash);
    }

}
