package br.com.alura.adopet.api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdocaoControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void deveriaDevolverCodigo400ParaSolicitacaoDeAdocaoComErros() {
		// ARRANGE
		String json = "{}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// ACT
		ResponseEntity<Void> response = restTemplate.exchange(
				"/adocoes",
				HttpMethod.POST,
				new HttpEntity<>(json, headers),
				Void.class
		);

		// ASSERT
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Disabled
	void deveriaDevolverCodigo200ParaSolicitacaoDeAdocaoSemErros() {
		// ARRANGE
		String json = """
                {
                    "idPet": 1,
                    "idTutor": 1,
                    "motivo": "Motivo qualquer"
                }
                """;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// ACT
		ResponseEntity<Void> response = restTemplate.exchange(
				"/adocoes",
				HttpMethod.POST,
				new HttpEntity<>(json, headers),
				Void.class
		);

		// ASSERT
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
