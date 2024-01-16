package org.launchcode.nextchapter.controllers;

import org.launchcode.nextchapter.data.ClubRepository;
import org.launchcode.nextchapter.models.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("clubs")
public class ClubController {

    @Autowired
    private ClubRepository clubRepository;

    @GetMapping
    public String displayClubInfo(Model model) {
        model.addAttribute("title", "Club Info");
        model.addAttribute("club", "INSERT CLUB INFO HERE");
        return "clubs/index";
    }


    @GetMapping("create")
    public String displayCreateClubForm(Model model) {
        model.addAttribute("title", "Create Club");
        model.addAttribute(new Club());
        return "clubs/create";
    }

    @PostMapping("create")
    public String processCreateClubForm(@ModelAttribute Club newClub, Errors errors, Model model){
        clubRepository.save(newClub);
        return "redirect:/clubs";
    }
}
