package org.launchcode.nextchapter.controllers;

import jakarta.validation.Valid;
import org.launchcode.nextchapter.data.ClubRepository;
import org.launchcode.nextchapter.models.Club;
import org.launchcode.nextchapter.models.dto.CreateClubFormDTO;
import org.launchcode.nextchapter.models.dto.RegisterFormDTO;
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
        model.addAttribute(new CreateClubFormDTO());
        model.addAttribute("title", "Create Club");
        return "clubs/create";
    }

    @PostMapping("create")
    public String processCreateClubForm(@ModelAttribute @Valid CreateClubFormDTO createClubFormDTO,
                                        Errors errors, Model model){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Club");
            return "clubs/create";
        }

        Club existingClub = clubRepository.findByDisplayName(createClubFormDTO.getDisplayName());

        if (existingClub != null) {
            errors.rejectValue("displayName", "displayName.alreadyexists", "A club with that username already exists");
            model.addAttribute("title", "Create Club");
            return "clubs/create";
        }

        String password = createClubFormDTO.getPassword();
        String verifyPassword = createClubFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Create Club");
            return "clubs/create";
        }

        Club newClub = new Club(createClubFormDTO.getDisplayName(),
                createClubFormDTO.getActiveBook(),
                createClubFormDTO.getPassword());
        clubRepository.save(newClub);

        return "redirect:/clubs";
    }
}
