package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculadoraProbabilidadeAdocaoTest {

	@Test
	@DisplayName("Probabilidade alta para gatos jovens com peso baixo")
	void probabilidadeAltaCenario1() {
		//Arrange
		Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
				"Abrigo feliz",
				"94999999999",
				"abrigofeliz@email.com.br"
		));
		Pet pet = new Pet(new CadastroPetDto(
				TipoPet.GATO,
				"Miau",
				"Siames",
				4,
				"Cinza",
				4.0f
		), abrigo);
		CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
		//Act
		var probabilidade = calculadora.calcular(pet);

		//Assert
		assertEquals(ProbabilidadeAdocao.ALTA, probabilidade);
	}

	@Test
	@DisplayName("Probabilidade média para gatos idosos com peso baixo")
	void probabilidadeMediaCenario1() {
		Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
				"Abrigo feliz",
				"94999999999",
				"abrigofeliz@email.com.br"
		));
		Pet pet = new Pet(new CadastroPetDto(
				TipoPet.GATO,
				"Miau",
				"Siames",
				15,
				"Cinza",
				4.0f
		), abrigo);
		CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
		var probabilidade = calculadora.calcular(pet);
		assertEquals(ProbabilidadeAdocao.MEDIA, probabilidade);
	}

	@Test
	@DisplayName("Probabilidade baixa para cachorros idosos com peso alto")
	void probabilidadeBaixaCenario1() {
		Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
				"Abrigo feliz",
				"94999999999",
				"abrigofeliz@email.com.br"
		));
		Pet pet = new Pet(new CadastroPetDto(
				TipoPet.CACHORRO,
				"Rex",
				"Vira-lata",
				10,
				"Marrom",
				20.0f
		), abrigo);
		CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
		var probabilidade = calculadora.calcular(pet);
		assertEquals(ProbabilidadeAdocao.BAIXA, probabilidade);
	}
}