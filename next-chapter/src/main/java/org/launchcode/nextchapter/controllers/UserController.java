package org.launchcode.nextchapter.controllers;

import jakarta.servlet.http.HttpSession;
import org.launchcode.nextchapter.data.MemberRepository;
import org.launchcode.nextchapter.models.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("members")
public class UserController {

    @Autowired
    private MemberRepository memberRepository;

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
    public String displayUserInfo(Model model) {
        model.addAttribute("title", "Member Info");
        model.addAttribute("user", "INSERT USER INFO HERE");
        return "members/index";
    }


    @GetMapping("create")
    public String displayCreateUserForm(Model model) {
        model.addAttribute("title", "Create Member");
        model.addAttribute(new Member());
        return "members/create";
    }

    @PostMapping("create")
    public String processCreateUserForm(@ModelAttribute Member newMember, Errors errors, Model model){
        memberRepository.save(newMember);
        return "redirect:/members";
    }
}
