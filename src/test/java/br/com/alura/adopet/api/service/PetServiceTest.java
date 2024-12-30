package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;


@ExtendWith(MockitoExtension.class)
class PetServiceTest {

	@InjectMocks
	private PetService petService;

	@Mock
	private CadastroPetDto dto;

	@Mock
	private PetRepository petRepository;

	@Mock
	private Abrigo abrigo;

	@Test
	@DisplayName("Deveria chamar listar e trazer todos os pets disponíveis")
	void buscarPetsDisponiveis() {
		petService.buscarPetsDisponiveis();
		then(petRepository).should().findAllByAdotadoFalse();
	}

	@Test
	@DisplayName("Deveria chamar salvar um novo pet")
	void cadastroPet() {
		petService.cadastrarPet(abrigo, dto);
		then(petRepository).should().save(new Pet(dto, abrigo));
	}
}