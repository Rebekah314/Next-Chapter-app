package org.launchcode.nextchapter.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.nextchapter.data.BlogRepository;
import org.launchcode.nextchapter.data.ClubRepository;
import org.launchcode.nextchapter.data.MemberRepository;
import org.launchcode.nextchapter.models.Blog;
import org.launchcode.nextchapter.models.Club;
import org.launchcode.nextchapter.models.Member;
import org.launchcode.nextchapter.models.dto.ClubMemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
   private ClubRepository clubRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public String displayAllBlogPosts(@RequestParam(required = false) Integer clubId, Model model) {
        if (clubId == null) {
            model.addAttribute("title", "Blog Info");
            model.addAttribute("club", "INSERT CLUB INFO HERE");
            model.addAttribute("blogs", blogRepository.findAll());
        } else {
            Optional<Club> result = clubRepository.findById(clubId);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid Club ID: " + clubId);
            } else {
                Club club = result.get();
                model.addAttribute("title", "Posts in " + club.getDisplayName());
                model.addAttribute("blogs", club.getBlogPosts());
            }
        }
        return "blog/index";
    }


//    @GetMapping("create")
//    public String displayCreateBlogForm(
//
////            Model model) {
////        model.addAttribute("title", "Create Post");
////        model.addAttribute("club", );
////        model.addAttribute(new Blog());
////        return "blog/create";
//
//            @RequestParam Integer clubId,
//            Model model, HttpSession session) {
//
//        Integer userId = (Integer) session.getAttribute("user");
//        Optional<Member> currentUser = memberRepository.findById(userId);
//        Optional<Club> clubResult = clubRepository.findById(clubId);
//
//        if (clubResult.isEmpty()) {
//            return "redirect:/";
//        } else if (currentUser.isEmpty()) {
////            Club club = clubResult.get();
//            model.addAttribute("title", "Please log in to create new post.");
//            return "members/login";
//        } else {
//            Member member = currentUser.get();
//            Club club = clubResult.get();
//            ClubMemberDTO clubMember = new ClubMemberDTO();
//            clubMember.setMember(member);
//            clubMember.setClub(club);
//
//            model.addAttribute("club", club);
//            model.addAttribute("clubId", clubId);
//            model.addAttribute("clubMember", clubMember);
//            model.addAttribute("title", "Create Post");
//            model.addAttribute(new Blog());
//        }
//
//        return "blog/create";
//    }


    @GetMapping("create")
    public String displayCreateBlogPost(@RequestParam Integer clubId,
                                        Model model, HttpSession session) {

        Optional<Club> result = clubRepository.findById(clubId);
        Club club = result.get();

        Integer userId = (Integer) session.getAttribute("user");
        Optional<Member> currentUser = memberRepository.findById(userId);
        Member member = currentUser.get();
        Blog blog = new Blog();
        blog.setBookContext(club.getActiveBook());

        model.addAttribute("title", "Create Post");
        model.addAttribute("club", club);
        model.addAttribute("member", member);
        model.addAttribute(blog);
        return "blog/create";
    }

    @PostMapping("create")
    public String processCreateBlogForm(@RequestParam Integer clubId, @RequestParam Integer memberId,
                                        @ModelAttribute @Valid Blog newBlog, Errors errors, Model model) {

//        if (errors.hasErrors()) {
//            return "redirect:/clubs/detail?clubId=" + clubId;
//        }

        if (errors.hasErrors()) {
            Optional<Club> result = clubRepository.findById(clubId);
            Club club = result.get();

            Optional<Member> currentUser = memberRepository.findById(memberId);
            Member member = currentUser.get();

        model.addAttribute("title", "Create Post");
        model.addAttribute("club", club);
        model.addAttribute("member", member);
        model.addAttribute("blog", newBlog);
        model.addAttribute("error message", "Please make sure all fields are filled out correctly.");
        return "blog/create";
    }

        Optional<Club> result = clubRepository.findById(clubId);
        Club club = result.get();

        Optional<Member> currentUser = memberRepository.findById(memberId);
        Member member = currentUser.get();

        newBlog.setMember(member);
        newBlog.setClub(club);
        blogRepository.save(newBlog);
//        Club club = newBlog.getClub();

//        model.addAttribute("title", club.getDisplayName());
        model.addAttribute("club", club);


        return "redirect:/clubs/detail?clubId=" + club.getId();
    }
}
