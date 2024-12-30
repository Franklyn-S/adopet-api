package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

	@InjectMocks
	private AbrigoService abrigoService;

	@Mock
	private AbrigoRepository abrigoRepository;

	@Mock
	private Abrigo abrigo;

	@Mock
	private PetRepository petRepository;

	@Test
	@DisplayName("Deveria chamar listar e trazer todos os abrigos")
	void listar() {
		abrigoService.listar();
		then(abrigoRepository).should().findAll();
	}

	@Test
	@DisplayName("Deveria chamar listar os pets do abrigo através do nome")
	void listarPetsDoAbrigo() {
		String nome = "Miau";
		given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));
		abrigoService.listarPetsDoAbrigo(nome);
		then(abrigoRepository).should().findByNome(nome);
	}

	@Test
	@DisplayName("Deveria chamar salvar um novo abrigo")
	void cadatrar() {
		CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo", "999999999", "abrigo@test.com");
		given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).willReturn(false);

		abrigoService.cadatrar(dto);
		then(abrigoRepository).should().save(new Abrigo(dto));
	}
}