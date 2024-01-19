package org.launchcode.nextchapter.controllers;

import org.launchcode.nextchapter.data.BlogRepository;
import org.launchcode.nextchapter.models.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @GetMapping
    public String displayClubInfo(Model model) {
        model.addAttribute("title", "Blog Info");
        model.addAttribute("club", "INSERT CLUB INFO HERE");
        model.addAttribute("blogs", blogRepository.findAll());
        return "blog/index";
    }


    @GetMapping("create")
    public String displayCreateClubForm(Model model) {
        model.addAttribute("title", "Create Post");
        model.addAttribute(new Blog());
        return "blog/create";
    }

    @PostMapping("create")
    public String processCreateClubForm(@ModelAttribute Blog newBlog, Errors errors, Model model){
        blogRepository.save(newBlog);
        return "redirect:/blog";
    }
}
