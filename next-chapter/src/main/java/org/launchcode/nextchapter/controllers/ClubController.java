package org.launchcode.nextchapter.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.nextchapter.data.BlogRepository;
import org.launchcode.nextchapter.data.ClubRepository;
import org.launchcode.nextchapter.data.MemberRepository;
import org.launchcode.nextchapter.models.Blog;
import org.launchcode.nextchapter.models.Club;
import org.launchcode.nextchapter.models.Member;
import org.launchcode.nextchapter.models.dto.AdminFormDTO;
import org.launchcode.nextchapter.models.dto.ClubMemberDTO;
import org.launchcode.nextchapter.models.dto.CreateClubFormDTO;
import org.launchcode.nextchapter.models.dto.LoginFormDTO;
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

    @GetMapping
    public String displayClubInfo(Model model) {
        model.addAttribute("title", "Club Info");
        model.addAttribute("club", "INSERT CLUB INFO HERE");
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
                createClubFormDTO.getActiveBook(),
                createClubFormDTO.getPassword(), userId);
        clubRepository.save(newClub);

        //Add club creator as first member of club
        if (!currentUser.isEmpty()) {
            Member member = currentUser.get();
            newClub.getMembers().add(member);
            clubRepository.save(newClub);
        }


        return "redirect:/clubs";
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

            if (errors.hasErrors()) {
                model.addAttribute("title", "Make Changes to " + club.getDisplayName());
                return "clubs/admin";
            }

            String password = adminFormDTO.getPassword();
            if (!club.isMatchingPassword(password)) {
                errors.rejectValue("password","password.invalid",
                        "Incorrect password");
                model.addAttribute("title", "Make Changes to " + club.getDisplayName());
                return "clubs/admin";
            }

            if (!(displayName == "")) {
                club.setDisplayName(displayName);
                clubRepository.save(club);
            }

            if (memberIds != null) {
                for (int id : memberIds) {
                    Optional<Member> result = memberRepository.findById(id);
                    Member member = result.get();
                    club.getMembers().remove(member);
                    clubRepository.save(club);
                }
            }

            if (blogIds != null) {
                for (int id : blogIds) {
                    blogRepository.deleteById(id);
                }
                blogPosts = club.getBlogPosts();
                Collections.reverse(blogPosts);
            }

            if (deleteClub && confirmDeleteClub) {
                clubRepository.deleteById(clubId);
                return "redirect:/";
            }
            model.addAttribute("blogs", blogPosts);
            model.addAttribute("club", club);
            return "redirect:/clubs/detail?clubId=" + clubId;
        }
    }

}
