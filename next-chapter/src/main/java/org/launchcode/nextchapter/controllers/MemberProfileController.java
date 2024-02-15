package org.launchcode.nextchapter.controllers;


import org.launchcode.nextchapter.data.MemberProfileRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class MemberProfileController {

    public final MemberProfileRepository profileRepository;

     MemberProfileController(MemberProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public MemberProfileRepository getProfileRepository() {
        return profileRepository;
    }

    public class Member {
        private String email;
        private String password;
        private String displayName;
        private List<String> clubsJoined;

        public Member(String email, String password, String displayName) {
            this.email = email;
            this.password = password;
            this.displayName = displayName;
            this.clubsJoined = new ArrayList<>();
        }

        public void updateEmail(String newEmail) {
            this.email = newEmail;
        }

        public void updatePassword(String newPassword) {
            this.password = newPassword;
        }

        public void updateDisplayName(String newDisplayName) {
            this.displayName = newDisplayName;
        }

        public void joinClub(String clubName) {
            clubsJoined.add(clubName);
        }

        public void deleteAccount() {
            // Perform account deletion logic
        }

        public String getDisplayName() {
            return displayName;
        }

        public List<String> getClubsJoined() {
            return clubsJoined;
        }
    }}
