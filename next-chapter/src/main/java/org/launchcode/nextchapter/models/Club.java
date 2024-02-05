package org.launchcode.nextchapter.models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Club extends AbstractEntity {

   @ManyToMany
    private List<Member> members = new ArrayList<>();
    //need to set up DTO, chapter 18.5

    private String activeBook;

    private String adminPwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Club(String displayName, String activeBook, String password) {
        this.setDisplayName(displayName);
        this.activeBook = activeBook;
        this.adminPwHash = encoder.encode(password);
    }

    public Club() {}

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
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
