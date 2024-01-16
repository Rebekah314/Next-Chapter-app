package org.launchcode.nextchapter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {
    @GetMapping
    public String displayUserInfo(Model model) {
        model.addAttribute("title", "User Info");
        model.addAttribute("user", "INSERT USER INFO HERE");
        return "user/index";
    }


    @GetMapping("create")
    public String displayCreateUserForm(Model model) {
        model.addAttribute("title", "Create User");
        //model.addAttribute(new User());
        return "user/create";
    }
}
