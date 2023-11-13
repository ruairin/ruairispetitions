/**
 * Main controller for the petitions application
 *
 * <p>Contains endpoints for the following features
 * of the petition application
 *  - view all
 *  - view one
 *  - create
 *  - search
 *  - sign
 * 
 * @author ruairin
 *
 */

package org.ruairispetitions.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.ruairispetitions.model.Petition;
import org.ruairispetitions.repository.PetitionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

@Controller
public class PetitionsController {

    // The spring data responsitory for the application
    // This is auto created by the framework
    // via the constructor argument below
    private final PetitionRepository repository;

    public PetitionsController(PetitionRepository repository) {
        this.repository = repository;
    }

    /*
     * View all peitions
     * 
     * <p>Returns the view containing all the data in the repository
     * 
     * @param model The spring container for model attributes
     * 
     * @return The name thymeleaf template to render the results
     * 
     */
    @GetMapping("/viewAll")
    public String viewAll(Model model) {
        model.addAttribute("petitions", this.repository.findAll());
        return "viewAll";
    }

    /*
     * View one peition
     * 
     * <p>Returns the view containing the requested petition. Returns
     * an error if the requested petition id is not found
     * 
     * @param id The repository id of the petition
     * 
     * @param model The spring container for model attributes
     * 
     * @return The name thymeleaf template to render the results or Not found http
     * status
     * 
     */
    @GetMapping("/view/{id}")
    public String viewOne(@PathVariable("id") Integer id, Model model) {

        Petition petition = this.repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found"));
        model.addAttribute("petition", petition);
        return "viewOne";
    }

    /*
     * Get the create form
     * 
     * <p>Returns the view containing the create form.
     * 
     * @param model The spring container for model attributes
     * 
     * @return The name thymeleaf template to render
     * 
     */
    @GetMapping("/create")
    public String create(Model model) {
        // Note: a Petition object must be added to the model
        // for the fields to be deserialised correctly in the subsequent
        // create (POST) handler
        Petition petition = new Petition();
        model.addAttribute("petition", petition);
        return "createForm";
    }

    /*
     * Create a new petition and return a view containg the outcome of
     * the create operation
     * 
     * <p>Returns a view containing a success message or an
     * error + status 400 if any of the required fields are empty or null
     * 
     * @param petition A Petition bean from the thymeleaf post request
     * 
     * @param model The spring container for model attributes
     * 
     * @param response A HttpServletResponse object, for setting the response status
     * if required
     * 
     * @return The name thymeleaf template to render
     * 
     */
    @PostMapping("/create")
    public String create(@ModelAttribute("petition") Petition petition, Model model, HttpServletResponse response) {

        // Check for blank title/description
        if (petition.getTitle() == null || petition.getTitle().isEmpty()
                || petition.getDescription() == null || petition.getDescription().isEmpty()) {

            response.setStatus(400);
            model.addAttribute("error", "Please specify a title and description for the petition");
            return "error";
        }

        // Add the current time/date to the petition bean before saving
        // to the repository
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        petition.setDate(now);

        repository.save(petition);

        return "createSuccess";
    }

    /*
     * Get the search form page
     * 
     * <p>Returns the view containing the search form.
     * 
     * @param model The spring container for model attributes
     * 
     * @return The name thymeleaf template to render
     * 
     */
    @GetMapping("/search")
    public String search(Model model) {
        return "searchForm";
    }

    /*
     * Perform the search for the search string and return the view containing the
     * results
     * 
     * <p>Returns the view containing the search results or an
     * error + status 400 if any of the required fields are empty or null
     * 
     * @param searchString The search term
     * 
     * @param model The spring container for model attributes
     * 
     * @param response A HttpServletResponse object, for setting the response status
     * if required
     * 
     * @return The name thymeleaf template to render
     * 
     */
    @GetMapping("/doSearch")
    public String doSearch(
            @RequestParam(value = "searchString", required = false) String searchString,
            Model model,
            HttpServletResponse response) {

        // Validate inputs
        if (searchString.isEmpty() || searchString == null) {
            response.setStatus(400);
            model.addAttribute("error", "A search term was not provided");
            return "error";
        }

        // Perform query and add to model
        List<Petition> results = repository.findAllByTitleIgnoreCaseContains(searchString);
        model.addAttribute("results", results);

        return "searchResults";
    }

    /*
     * Add a signature to the selected petition
     * 
     * <p>Returns the view containing the signed petition or
     * error + status 400 if any of the required fields are empty or null
     * 
     * @param id The integer id of the petition
     * 
     * @param email The email for the signature
     * 
     * @param name The name for the signature
     * 
     * @param model The spring container for model attributes
     * 
     * @param response A HttpServletResponse object, for setting the response status
     * if required
     * 
     * @return The name thymeleaf template to render
     * 
     */
    @GetMapping("/sign")
    public String sign(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "name", required = true) String name,
            Model model,
            HttpServletResponse response) {

        // Validate inputs
        if (email.isEmpty() || email == null ||
                name.isEmpty() || name == null) {
            response.setStatus(400);
            model.addAttribute("error", "Name and email are required to sign");
            return "error";
        }

        // Find the record and append signature to existing
        Petition petition = this.repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found"));
        petition.appendSignatures(name + " (" + email + ")");
        repository.save(petition);

        return "redirect:/view/" + id;
    }
}
