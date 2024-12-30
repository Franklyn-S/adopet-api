package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
class TutorControllerTest {

	@MockBean
	private TutorService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Deveria devolver código 200 para requisição de cadastro de tutor")
	void cadastrarSucesso() throws Exception {
		// Arrange
		String json = """
				{
					"nome": "Fulano",
					"telefone": "(85)999999999",
					"email": "email@example.com"
				}
				""";

		// Act
		var response = mockMvc.perform(
				post("/tutores")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
		).andReturn().getResponse();

		//Assert
		assertEquals(200, response.getStatus());
	}

	@Test
	@DisplayName("Deveria devolver código 400 para requisição de cadastro de tutor com erros")
	void cadastrarErro400() throws Exception {
		// Arrange
		String json = """
				{
					"nome": "Fulano",
					"telefone": "(85)9999-99999",
					"email": "email@example.com"
				}
				""";

		// Act
		var response = mockMvc.perform(
				post("/tutores")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
		).andReturn().getResponse();

		//Assert
		assertEquals(400, response.getStatus());

	}

	@Test
	@DisplayName("Deveria devolver código 200 para requisição de atualizar tutor")
	void atualizarSucesso() throws Exception {
		String json = """
				{
					"id": "1",
					"nome": "Fulano",
					"telefone": "(85)123456789",
					"email": "email@example.com"
				}
				""";
		// Act
		var response = mockMvc.perform(
				put("/tutores")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
		).andReturn().getResponse();

		//Assert
		assertEquals(200, response.getStatus());
	}

	@Test
	@DisplayName("Deveria devolver código 400 para requisição de atualizar tutor com erro")
	void atualizarComErroDeValidacao() throws Exception {
		String json = """
				{
					"id": "1",
					"nome": "Fulano",
					"telefone": "(85)1234567890",
					"email": "email@example.com"
				}
				""";
		// Act
		var response = mockMvc.perform(
				put("/tutores")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
		).andReturn().getResponse();

		//Assert
		assertEquals(400, response.getStatus());
	}
}