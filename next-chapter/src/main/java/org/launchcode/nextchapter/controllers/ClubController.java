package org.launchcode.nextchapter.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.nextchapter.data.BlogRepository;
import org.launchcode.nextchapter.data.ClubRepository;
import org.launchcode.nextchapter.data.MemberRepository;
import org.launchcode.nextchapter.models.Blog;
import org.launchcode.nextchapter.models.Club;
import org.launchcode.nextchapter.models.Member;
import org.launchcode.nextchapter.models.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("clubs")
public class ClubController {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private BlogRepository blogRepository;

    @GetMapping("home")
    public String displayClubInfo(Model model, HttpSession session) {

        //Check if user is logged in. If so, club buttons link to club page.
        Integer userId = (Integer) session.getAttribute("user");

        if (userId == null) {
            model.addAttribute("existingMember", false);
        } else {
            Optional<Member> currentUser = memberRepository.findById(userId);
            if (!currentUser.isEmpty()) {
                model.addAttribute("existingMember", true);
            } else {
                model.addAttribute("existingMember", false);
            }
        }
        model.addAttribute("title", "Browse All Clubs");
        model.addAttribute("clubs", clubRepository.findAll());
        return "clubs/index";
    }

    @GetMapping("create")
    public String displayCreateClubForm(Model model) {
        model.addAttribute(new CreateClubFormDTO());
        model.addAttribute("title", "Create Club");
        return "clubs/create";
    }

    @PostMapping("create")
    public String processCreateClubForm(@ModelAttribute @Valid CreateClubFormDTO createClubFormDTO,
                                        Errors errors, Model model, HttpSession session){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Club");
            return "clubs/create";
        }

        Club existingClub = clubRepository.findByDisplayName(createClubFormDTO.getDisplayName());

        //check if a club with that name already exists
        if (existingClub != null) {
            errors.rejectValue("displayName", "displayName.alreadyexists", "A club with that username already exists");
            model.addAttribute("title", "Create Club");
            return "clubs/create";
        }

        String password = createClubFormDTO.getPassword();
        String verifyPassword = createClubFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Create Club");
            return "clubs/create";
        }

        Integer userId = (Integer) session.getAttribute("user");
        Optional<Member> currentUser = memberRepository.findById(userId);

        Club newClub = new Club(createClubFormDTO.getDisplayName(),
                createClubFormDTO.getDescription(),
                createClubFormDTO.getActiveBook(),
                createClubFormDTO.getPassword(), userId);
        clubRepository.save(newClub);

        //Add club creator as first member of club
        if (!currentUser.isEmpty()) {
            Member member = currentUser.get();
            newClub.getMembers().add(member);
            clubRepository.save(newClub);
        }
        Integer clubId = newClub.getId();


        return "redirect:/clubs/detail?clubId=" + clubId;
    }

    @GetMapping("detail")
    public String displayClubDetails(@RequestParam Integer clubId,
                                     Model model, HttpSession session) {

        Optional<Club> result = clubRepository.findById(clubId);
        Integer userId = (Integer) session.getAttribute("user");
        Optional<Member> currentUser = memberRepository.findById(userId);

        if (result.isEmpty()) {
            return "redirect:/";
        } else {
            Club club = result.get();
            List<Blog> blogPosts = club.getBlogPosts();
            Collections.reverse(blogPosts);
            model.addAttribute("title", club.getDisplayName());
            model.addAttribute("club", club);
            model.addAttribute("blogs", blogPosts);

            if (currentUser.isEmpty()) {
                return "redirect:/";
            }
            Member member = currentUser.get();

            if(club.getMembers().contains(member)) {
                model.addAttribute("existingMember", true);
            } else {
                model.addAttribute("existingMember", false);
            }
        }

        return "clubs/detail";

    }

    @GetMapping("join")
    public String displayJoinClubForm(@RequestParam Integer clubId,
                                      Model model, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user");
        Optional<Member> currentUser = memberRepository.findById(userId);
        Optional<Club> clubResult = clubRepository.findById(clubId);

        if (clubResult.isEmpty()) {
            return "redirect:/";
        } else if (currentUser.isEmpty()) {
            Club club = clubResult.get();
            model.addAttribute("title", "Please log in to join " + club.getDisplayName());
            return "clubs/join";
        } else {
                Member member = currentUser.get();
                Club club = clubResult.get();
                ClubMemberDTO clubMember = new ClubMemberDTO();
                clubMember.setMember(member);
                clubMember.setClub(club);

                model.addAttribute("title", "Join " + club.getDisplayName());
                model.addAttribute("club", club);
                model.addAttribute("clubId", clubId);
                model.addAttribute("clubMember", clubMember);
        }

        return "clubs/join";
    }

    @PostMapping("join")
    public String processJoinClubForm(@ModelAttribute @Valid ClubMemberDTO clubMember,
                                      Errors errors, Model model) {
        if(!errors.hasErrors()) {
            Member member = clubMember.getMember();
            Club club = clubMember.getClub();
            if(!club.getMembers().contains(member)) {
                club.getMembers().add(member);
                clubRepository.save(club);
            }
            model.addAttribute("title", club.getDisplayName());
            model.addAttribute("club", club);
            model.addAttribute("existingMember", true);
            return "redirect:/clubs/detail?clubId=" + club.getId();
        }
        return "redirect:clubs/join";
    }

    @GetMapping("leave")
    public String displayLeaveClubForm(@RequestParam Integer clubId,
                                       Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("user");
        Optional<Member> currentUser = memberRepository.findById(userId);
        Optional<Club> clubResult = clubRepository.findById(clubId);

        if (clubResult.isEmpty() || currentUser.isEmpty()) {
            return "redirect:/";
        } else {
            Member member = currentUser.get();
            Club club = clubResult.get();


            model.addAttribute("title", "Leave " + club.getDisplayName());
            model.addAttribute("club", club);
            model.addAttribute("member", member);
        }

        return "clubs/leave";
    }

    @PostMapping("leave")
    public String processLeaveClubForm(@RequestParam int clubId,
                                       Model model, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user");
        Optional<Member> currentUser = memberRepository.findById(userId);
        Optional<Club> clubResult = clubRepository.findById(clubId);
        if (clubResult.isEmpty() || currentUser.isEmpty()) {
            return "redirect:/";
        } else {
            Club club = clubResult.get();
            Member member = currentUser.get();
            List<Member> memberList = club.getMembers();
            memberList.remove(member);
            club.setMembers(memberList);
            clubRepository.save(club);

            model.addAttribute("title", club.getDisplayName());
            model.addAttribute("club", club);
            model.addAttribute("existingMember", false);
            return "clubs/detail";
        }
    }

    @GetMapping("admin")
    public String displayClubAdminForm(@RequestParam int clubId, Model model) {
        Optional<Club> clubResult = clubRepository.findById(clubId);
        if (clubResult.isEmpty()) {
            return "redirect:/";
        } else {
            Club club = clubResult.get();

            List<Blog> blogPosts = club.getBlogPosts();
            Collections.reverse(blogPosts);

            model.addAttribute("title", "Make Changes to " + club.getDisplayName());
            model.addAttribute("club", club);
            model.addAttribute("blogs", blogPosts);
            model.addAttribute("existingMember", true);
            model.addAttribute(new AdminFormDTO());

        }
        return "clubs/admin";
    }

    @PostMapping("admin")
    public String processClubAdminForm(@ModelAttribute @Valid AdminFormDTO adminFormDTO, Errors errors,
                                       @RequestParam int clubId, @RequestParam(required = false) String displayName,
                                       @RequestParam(required = false) String description,
                                       @RequestParam(required = false) boolean confirmDeleteClub,
                                       @RequestParam(required = false) boolean deleteClub,
                                       @RequestParam(required = false) int[] blogIds,
                                       @RequestParam(required = false) int[] memberIds, Model model) {

        Optional<Club> clubResult = clubRepository.findById(clubId);
        if (clubResult.isEmpty()) {
            return "redirect:/";
        } else {
            Club club = clubResult.get();
            List<Blog> blogPosts = club.getBlogPosts();
            Collections.reverse(blogPosts);
            model.addAttribute("club", club);
            model.addAttribute("blogs", blogPosts);
            model.addAttribute("existingMember", true);
            model.addAttribute("clubId", clubId);
            model.addAttribute("title", "Make Changes to " + club.getDisplayName());


            if (errors.hasErrors()) {
                return "clubs/admin";
            }

            //If password is incorrect, display error
            String password = adminFormDTO.getPassword();
            if (!club.isMatchingPassword(password)) {
                errors.rejectValue("password","password.invalid",
                        "Incorrect password");
                return "clubs/admin";
            }

            //If displayName is not blank, update field
            if (!(displayName == "")) {
                Club existingClub = clubRepository.findByDisplayName(displayName);
                if (existingClub != null) {
                    model.addAttribute("displayNameError",
                            "Cannot rename club [" + displayName + "] because a club with that name already exists");
                    return "clubs/admin";
                }
                club.setDisplayName(displayName);
                clubRepository.save(club);
            }

            //If description is not blank, update field
            if (!(description == "")) {
                if (description.length() > 500) {
                    model.addAttribute("descriptionError",
                            "Invalid club description. Must be between 1 and 500 characters.");
                    return "clubs/admin";
                }
                club.setDescription(description);
                clubRepository.save(club);
            }

            //if memberIds is not empty, remove any members with matching ids
            if (memberIds != null) {
                for (int id : memberIds) {
                    Optional<Member> result = memberRepository.findById(id);
                    Member member = result.get();
                    club.getMembers().remove(member);
                    clubRepository.save(club);
                }
            }

            //if blogIds is not empty, remove any blog posts with matching ids
            if (blogIds != null) {
                for (int id : blogIds) {
                    blogRepository.deleteById(id);
                }
                blogPosts = club.getBlogPosts();
                Collections.reverse(blogPosts);
            }

            //If both boxes were checked to delete a club, then delete the club from the repository
            if (deleteClub && confirmDeleteClub) {
                clubRepository.deleteById(clubId);
                return "redirect:/";
            }

            //If everything was fine, return to the club detail page and display updated information
            model.addAttribute("blogs", blogPosts);
            model.addAttribute("club", club);
            return "redirect:/clubs/detail?clubId=" + clubId;
        }
    }

    @GetMapping("update-password")
    public String displayUpdateAdminPasswordForm(@RequestParam int clubId, Model model) {

        Optional<Club> clubResult = clubRepository.findById(clubId);
        if (clubResult.isEmpty()) {
            return "redirect:/";
        } else {
            Club club = clubResult.get();

            model.addAttribute("title", "Change Password for " + club.getDisplayName());
            model.addAttribute("clubId", club.getId());
            model.addAttribute(new AdminUpdatePasswordDTO());
            model.addAttribute("passwordUpdated", false);

        }
        return "clubs/update-password";
    }

    @PostMapping("update-password")
    public String processUpdateAdminPasswordForm(@ModelAttribute @Valid AdminUpdatePasswordDTO adminUpdatePasswordDTO, Errors errors,
                                                 @RequestParam int clubId, Model model) {

        Optional<Club> clubResult = clubRepository.findById(clubId);
        model.addAttribute("passwordUpdated", false);

        if (clubResult.isEmpty()) {
            return "redirect:/";
        } else {
            Club club = clubResult.get();
            model.addAttribute("title", "Change Password for " + club.getDisplayName());
            model.addAttribute("clubId", club.getId());
            if (errors.hasErrors()) {
                return "clubs/update-password";
            }

            //Display an error if both new passwords do not match
            String newPassword = adminUpdatePasswordDTO.getNewPassword();
            String verifyNewPassword = adminUpdatePasswordDTO.getVerifyNewPassword();
            if (!newPassword.equals(verifyNewPassword)) {
                errors.rejectValue("newPassword", "passwords.mismatch", "Passwords do not match");
                return "clubs/update-password";
            }

            //Check if new password is the same as the old password
            if (club.isMatchingPassword(newPassword)) {
                model.addAttribute("passwordsMatchError", "New password must be different than current password.");
            }

            //Check if old password was entered correctly
            String oldPassword = adminUpdatePasswordDTO.getOldPassword();
            if (!club.isMatchingPassword(oldPassword)) {
                errors.rejectValue("oldPassword", "password.invalid",
                        "Incorrect password");
                return "clubs/update-password";
            }

            //Save new password to club
            club.setAdminPwHash(newPassword);
            clubRepository.save(club);
            model.addAttribute("passwordUpdated", true);


        }

        return "clubs/update-password";
    }

}
