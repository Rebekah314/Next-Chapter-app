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

    //The method is posted in each Controller
    public Member getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("user");
        if (userId == null) {
            return null;
        }
        Optional<Member> user = memberRepository.findById(userId);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
   private ClubRepository clubRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public String displayAllBlogPosts(@RequestParam(required = false) Integer clubId, Model model, HttpSession session) {

        model.addAttribute("member", getUserFromSession(session));

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


    @GetMapping("create")
    public String displayCreateBlogPost(@RequestParam Integer clubId,
                                        Model model, HttpSession session) {

        Member member = getUserFromSession(session);
        model.addAttribute("member", member);

        Optional<Club> result = clubRepository.findById(clubId);
        Club club = result.get();

        Blog blog = new Blog();
        blog.setBookContext(club.getActiveBook());

        model.addAttribute("title", "Create Post");
        model.addAttribute("club", club);
        model.addAttribute(blog);
        return "blog/create";
    }

    @PostMapping("create")
    public String processCreateBlogForm(@RequestParam Integer clubId, @RequestParam Integer memberId,
                                        @ModelAttribute @Valid Blog newBlog, Errors errors, Model model,
                                        HttpSession session) {

        Member member = getUserFromSession(session);
        model.addAttribute("member", member);

        Optional<Club> result = clubRepository.findById(clubId);
        Club club = result.get();
        model.addAttribute("club", club);


        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Post");
            model.addAttribute("blog", newBlog);
            model.addAttribute("error message", "Please make sure all fields are filled out correctly.");
            return "blog/create";
        }

        newBlog.setMember(member);
        newBlog.setClub(club);
        blogRepository.save(newBlog);

        return "redirect:/clubs/detail?clubId=" + club.getId();
    }
}
