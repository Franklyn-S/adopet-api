package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {

	@InjectMocks
	private ValidacaoPetDisponivel validacao;

	@Mock
	private Pet pet;

	@Mock
	private PetRepository petRepository;

	@Mock
	private SolicitacaoAdocaoDto dto;

	@Test
	@DisplayName("Pet disponível para adoção")
	void petDisponivelParaAdocao() {
		BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
		BDDMockito.given(pet.getAdotado()).willReturn(false);
		Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
	}

	@Test
	@DisplayName("Pet não disponível para adoção deve lançar exceção")
	void petNaoDisponivelParaAdocao() {
		BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
		BDDMockito.given(pet.getAdotado()).willReturn(true);
		Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
	}
}