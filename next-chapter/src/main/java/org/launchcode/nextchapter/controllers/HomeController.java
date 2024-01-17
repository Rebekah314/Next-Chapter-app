package org.launchcode.nextchapter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/* Created by Rebekah Garris */

@Controller
public class HomeController {


    @GetMapping
    public String index(Model model) {

        model.addAttribute("title", "Welcome to your Next Chapter!");
        return "index";
    }
}
