package org.ruairispetitions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class PetitionsController {

    @GetMapping("/viewAll")
    public String viewAll(Model model) {
        model.addAttribute("petitions", "Petition #1");
        return "showPetitions";
    }

}
