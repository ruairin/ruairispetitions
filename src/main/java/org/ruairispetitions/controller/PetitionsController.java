package org.ruairispetitions.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.ruairispetitions.model.Petition;
import org.ruairispetitions.repository.PetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
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

    @GetMapping("/view/{id}")
    public String viewOne(@PathVariable("id") Integer id, Model model) {

        Petition petition = this.repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found"));
        model.addAttribute("petition", petition);
        return "viewOne";
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

    @GetMapping("/sign")
    public String sign(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "name", required = true) String name,
            Model model) {

        if (email.isEmpty() || email == null ||
                name.isEmpty() || name == null) {
            model.addAttribute("error", "Name and email are required to sign");
            return "error";
        }

        Petition petition = this.repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found"));
        petition.appendSignatures(name + " (" + email + ")");
        repository.save(petition);

        return "redirect:/view/" + id;
    }
}
