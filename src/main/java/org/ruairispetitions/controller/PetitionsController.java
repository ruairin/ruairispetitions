package org.ruairispetitions.controller;

import org.ruairispetitions.repository.PetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class PetitionsController {

    private final PetitionRepository repository;

    public PetitionsController(PetitionRepository repository){
        this.repository = repository;
    }

    @GetMapping("/viewAll")
    public String viewAll(Model model) {
        model.addAttribute("petitions", this.repository.findAll());
        return "showAllPetitions";
    }

}
