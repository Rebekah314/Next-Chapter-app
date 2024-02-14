package org.launchcode.nextchapter.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private final String role;
    private String book;

    @ManyToMany
    @JoinTable(name = "member_profile_club",
            joinColumns = @JoinColumn(name = "member_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id"))
    private List<Club> clubs;

    // Constructors, getters, and setters (you can use Lombok to simplify)

    // Default constructor
    public MemberProfile(String role) {
        this.role = role;
    }

    // Parameterized constructor
    public MemberProfile(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.book = book;
    }

    // Getters and setters for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return book;
    }

    public void setRole(String role) {
        this.book = role;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }
}
