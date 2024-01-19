package org.launchcode.nextchapter.controllers;

import org.launchcode.nextchapter.data.UserRepository;
import org.launchcode.nextchapter.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String displayUserInfo(Model model) {
        model.addAttribute("title", "User Info");
        model.addAttribute("user", "INSERT USER INFO HERE");
        return "users/index";
    }


    @GetMapping("create")
    public String displayCreateUserForm(Model model) {
        model.addAttribute("title", "Create User");
        model.addAttribute(new User());
        return "users/create";
    }

    @PostMapping("create")
    public String processCreateUserForm(@ModelAttribute User newUser, Errors errors, Model model){
        userRepository.save(newUser);
        return "redirect:/users";
    }
}
