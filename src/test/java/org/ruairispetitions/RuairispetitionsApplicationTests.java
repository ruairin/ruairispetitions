package org.ruairispetitions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.ruairispetitions.controller.PetitionsController;
import org.ruairispetitions.model.Petition;
import org.ruairispetitions.repository.PetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@WebMvcTest(PetitionsController.class)
class RuairispetitionsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	PetitionRepository petitionRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
		Optional<Petition> petition = Optional.of(new Petition(1, "Petition 1", "Description 1", now.format(formatter), "John Joe"));
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
}
