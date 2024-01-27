package org.launchcode.nextchapter.controllers;

import org.launchcode.nextchapter.data.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/* Created by Rebekah Garris */

@Controller
public class HomeController {

    @Autowired
    private ClubRepository clubRepository;


    @GetMapping
    public String index(Model model) {

        model.addAttribute("title", "Welcome to your Next Chapter!");
        model.addAttribute("clubs", clubRepository.findAll());
        return "index";
    }

    @GetMapping("home")
    public String home(Model model) {

        model.addAttribute("title", "Welcome to your Next Chapter!");
        model.addAttribute("clubs", clubRepository.findAll());
        return "index";
    }
}
