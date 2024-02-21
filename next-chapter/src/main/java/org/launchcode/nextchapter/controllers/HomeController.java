package org.launchcode.nextchapter.controllers;

import jakarta.servlet.http.HttpSession;
import org.launchcode.nextchapter.data.ClubRepository;
import org.launchcode.nextchapter.data.MemberRepository;
import org.launchcode.nextchapter.models.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

/* Created by Rebekah Garris */

@Controller
public class HomeController {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    MemberRepository memberRepository;

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


    @GetMapping
    public String index(Model model) {

        return "redirect:/home";
    }

    @GetMapping("home")
    public String home(Model model, HttpSession session) {

        model.addAttribute("title", "Welcome to your Next Chapter!");
        model.addAttribute("clubs", clubRepository.findAll());
        model.addAttribute("member", getUserFromSession(session));

        return "index";
    }
}
