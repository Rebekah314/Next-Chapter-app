package org.launchcode.nextchapter.models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static javax.swing.text.html.HTML.Tag.HEAD;

@Entity
public class Club extends AbstractEntity {

   @ManyToMany
    private List<Member> members = new ArrayList<>();
    //need to set up DTO, chapter 18.5

    private String activeBook;

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


    private String coverId = null;

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }


    public Club(String displayName, String description, String activeBook, String password, int adminId) {
        this.setDisplayName(displayName);
        this.description = description;
        this.activeBook = activeBook;
<<<<<<< HEAD
        CharSequence adminPw = null;
        this.adminPwHash = encoder.encode(adminPw);

        this.adminPwHash = encoder.encode(password);
        this.adminId = adminId;
>>>>>>> origin/main
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

<<<<<<< HEAD
    public void setAdminPwHash(String adminPwHash) {
        this.adminPwHash = adminPwHash;
    }
=======
    public List<Blog> getBlogPosts() {
        return blogPosts;
    }


>>>>>>> origin/main
}
