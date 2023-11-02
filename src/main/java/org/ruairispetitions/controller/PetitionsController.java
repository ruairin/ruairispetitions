package org.ruairispetitions.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.ruairispetitions.model.Petition;
import org.ruairispetitions.repository.PetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class PetitionsController {

    private final PetitionRepository repository;

    public PetitionsController(PetitionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/viewAll")
    public String viewAll(Model model) {
        model.addAttribute("petitions", this.repository.findAll());
        return "showAllPetitions";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Petition petition = new Petition();
        model.addAttribute("petition", petition);
        return "createPetition";
    }

    @PostMapping("/createPetition")
    public String createPetition(@ModelAttribute("petition") Petition petition, Model model) {

        // Check for blank title/description
        if (petition.getTitle() == null || petition.getTitle().isEmpty()
                || petition.getDescription() == null || petition.getDescription().isEmpty()) {
            model.addAttribute("error", "Please specify a title and description for the petition");
            return "error";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        petition.setDate(now.format(formatter));

        repository.save(petition);

        return "petitionCreated";
    }

    @GetMapping("/search")
    public String search(Model model) {
        return "search";
    }

    @GetMapping("/doSearch")
    public String doSearch(
            @RequestParam(value = "searchString", required = false) String searchString,
            Model model) {

        if (searchString.isEmpty() || searchString == null) {
            model.addAttribute("error", "A search term was not provided");
            return "error";
        }

        List<Petition> results = repository.findAllByTitleIgnoreCaseContains(searchString);
        model.addAttribute("results", results);

        return "searchResults";
    }
}
