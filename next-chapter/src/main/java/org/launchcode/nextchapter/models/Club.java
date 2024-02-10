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

    private int adminId;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Club(String displayName, String activeBook, String password, int adminId) {
        this.setDisplayName(displayName);
        this.activeBook = activeBook;
        this.adminPwHash = encoder.encode(password);
        this.adminId = adminId;
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

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setAdminPwHash(String adminPwHash) {
        this.adminPwHash = adminPwHash;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, adminPwHash);
    }

}
