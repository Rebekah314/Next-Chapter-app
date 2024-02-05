package org.launchcode.nextchapter.models.dto;

import jakarta.validation.constraints.NotNull;
import org.launchcode.nextchapter.models.Club;
import org.launchcode.nextchapter.models.Member;

public class ClubMemberDTO {

    @NotNull
    private Club club;

    @NotNull
    private Member member;

    public ClubMemberDTO() {}

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
