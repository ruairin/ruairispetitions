package org.ruairispetitions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.ruairispetitions.controller.PetitionsController;
import org.ruairispetitions.model.Petition;
import org.ruairispetitions.repository.PetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.Optional;

@WebMvcTest(PetitionsController.class)
class PetitionsControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	PetitionRepository petitionRepository;

	@MockBean 
	Petition petition;

	@Autowired
	ObjectMapper objectMapper;

	// ============ Tests for viewall feature ===============
	@Test
	void shouldShowAllPetitions() throws Exception {
		this.mockMvc
				.perform(get("/viewAll"))
				.andExpect(status().isOk())
				.andExpect(view().name("viewAll"))
				.andExpect(model().attributeExists("petitions"));
	}

	// ============ Tests for vieOne feature ===============
	@Test
	void shouldShowOnePetition() throws Exception{
        LocalDateTime now = LocalDateTime.now();
		Optional<Petition> petition = Optional.of(new Petition(1, "Petition 1", "Description 1", now, "John Joe"));
		Mockito.when(petitionRepository.findById(1)).thenReturn(petition);

		this.mockMvc
			.perform(get("/view/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("viewOne"))
			.andExpect(model().attributeExists("petition"));
	}

	@Test
	void shouldFailToShowOnePetition() throws Exception{
		Optional<Petition> petition = Optional.empty();
		Mockito.when(petitionRepository.findById(100)).thenReturn(petition);

		this.mockMvc
			.perform(get("/view/100"))
			.andExpect(status().isNotFound());
	}

	// ============ Tests for create feature ===============
	@Test
	void shouldGetCreatePage() throws Exception {
		this.mockMvc
				.perform(get("/create"))
				.andExpect(status().isOk())
				.andExpect(view().name("createForm"))
				.andExpect(model().attributeExists("petition"));
	}

	@Test
	void shouldCreateNewPetition() throws Exception {
		this.mockMvc
		 	.perform(post("/create")
		 		.param("title", "Test Title")
				.param("description", "Test Description"))
			.andExpect(status().isOk())
			.andExpect(view().name("createSuccess"));
	}

	@Test
	void shouldFailToCreateNewPetition() throws Exception {
		this.mockMvc
		 	.perform(post("/create")
		 		.param("title", "")
				.param("description", "Test Description"))
			.andExpect(status().isBadRequest())
			.andExpect(view().name("error"));
	}

	// ============ Tests for search feature ===============
	@Test
	void shouldGetSearchPage() throws Exception {
		this.mockMvc
				.perform(get("/search"))
				.andExpect(status().isOk())
				.andExpect(view().name("searchForm"));
	}

	@Test
	void shouldGetSearchResults() throws Exception {
		this.mockMvc
			.perform(get("/doSearch")
				.param("searchString", "Petition"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("results"))
			.andExpect(view().name("searchResults"));
	}

	@Test
	void shouldFailToGetSearchResults() throws Exception {
		this.mockMvc
			.perform(get("/doSearch")
				.param("searchString", ""))
			.andExpect(status().isBadRequest())
			.andExpect(model().attributeExists("error"))
			.andExpect(view().name("error"));
	}

	// ============ Tests for sign feature ===============
	@Test
	void shouldSignPetition() throws Exception {
        LocalDateTime now = LocalDateTime.now();
		Optional<Petition> petition = Optional.of(new Petition(1, "Petition 1", "Description 1", now, "John Joe"));
		Mockito.when(petitionRepository.findById(1)).thenReturn(petition);

		this.mockMvc
			.perform(get("/sign")
				.param("id", "1")
				.param("email", "test@domain.com")
				.param("name", "Ted"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/view/1"));
	}

	@Test
	void shouldFailToSignPetition() throws Exception {
		this.mockMvc
			.perform(get("/sign")
				.param("id", "1")
				.param("email", "test@domain.com")
				.param("name", ""))
			.andExpect(status().isBadRequest())
			.andExpect(model().attributeExists("error"))
			.andExpect(view().name("error"));
	}

}	
