package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

	@MockBean
	private AbrigoService abrigoService;

	@MockBean
	private PetService petService;

	@Mock
	private Abrigo abrigo;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Deveria devolver código 200 para requisição de listagem de abrigos")
	void listar() throws Exception {
		// ACT
		MockHttpServletResponse response = mockMvc.perform(
				get("/abrigos")
		).andReturn().getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
	}
}