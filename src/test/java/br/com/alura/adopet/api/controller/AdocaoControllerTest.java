package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AdocaoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdocaoService adocaoService;

	@Autowired
	private JacksonTester<SolicitacaoAdocaoDto> jsonDto;

	@Test
	@DisplayName("Deveria devolver código 400 para solicitação de adoção com erros")
	void cenarioComErrosDeValidacao() throws Exception {
		// Arrange
		String json = "{}";
		// Act
		var response = mockMvc.perform(
				post("/adocoes")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
		).andReturn().getResponse();
		// Assert
		assertEquals(400, response.getStatus());
	}

	@Test
	@DisplayName("Deveria devolver código 200 para solicitação de adoção com sucesso")
	void cenarioCodigo200() throws Exception {
		// Arrange
		SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1l, 1l, "Motivo qualquer");

		// Act
		var response = mockMvc.perform(
				post("/adocoes")
						.content(jsonDto.write(dto).getJson())
						.contentType(MediaType.APPLICATION_JSON)
		).andReturn().getResponse();
		// Assert
		assertEquals(200, response.getStatus());
	}
}