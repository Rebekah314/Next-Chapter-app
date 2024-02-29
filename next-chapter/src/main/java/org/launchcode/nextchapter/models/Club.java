package org.launchcode.nextchapter.models;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Club extends AbstractEntity {

   @ManyToMany
    private List<Member> members = new ArrayList<>();
    //need to set up DTO, chapter 18.5



    private String adminPwHash;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private int adminId;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @OneToMany(mappedBy = "club")
    private final List<Blog> blogPosts = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private ActiveBook activeBook;


    private String coverId = null;

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }


    public Club(String displayName, String description, String password, int adminId) {
        this.setDisplayName(displayName);
        this.description = description;
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

    public ActiveBook getActiveBook() {
        return activeBook;
    }

    public void setActiveBook(ActiveBook activeBook) {
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

    public void setAdminPwHash(String password) {
        this.adminPwHash = encoder.encode(password);
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, adminPwHash);
    }

    public List<Blog> getBlogPosts() {
        return blogPosts;
    }


}
