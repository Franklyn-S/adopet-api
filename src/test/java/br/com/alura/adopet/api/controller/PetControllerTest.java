package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

	@MockBean
	private PetService petService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Deveria devolver código 200 para requisição de listagem de pets")
	void listarTodosDisponiveis() throws Exception {
		var response = mockMvc.perform(
				get("/pets")
						.contentType(MediaType.APPLICATION_JSON)
		).andReturn().getResponse();

		assertEquals(200, response.getStatus());

	}
}