package org.launchcode.nextchapter.models.dto;

import jakarta.validation.constraints.NotNull;
import org.launchcode.nextchapter.models.Club;
import org.launchcode.nextchapter.models.User;

public class ClubMemberDTO {

    @NotNull
    private Club club;

    @NotNull
    private User member;

    public ClubMemberDTO() {}

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }
}
