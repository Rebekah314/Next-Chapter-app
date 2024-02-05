package org.launchcode.nextchapter.controllers;

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

@Controller
@RequestMapping("members")
public class UserController {

    @Autowired
    private MemberRepository memberRepository;

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
