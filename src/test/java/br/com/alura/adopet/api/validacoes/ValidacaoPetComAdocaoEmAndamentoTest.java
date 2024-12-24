package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {

	@InjectMocks
	private ValidacaoPetComAdocaoEmAndamento validacao;

	@Mock
	private AdocaoRepository adocaoRepository;

	@Mock
	private SolicitacaoAdocaoDto dto;

	@Test
	@DisplayName("Pet com adoção em andamento deve lançar exceção")
	void petComAdocaoEmAndamento() {
		BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
		assertEquals("Pet já está aguardando avaliação para ser adotado!", exception.getMessage());
	}

	@Test
	@DisplayName("Pet sem adoção em andamento não deve lançar exceção")
	void petSemAdocaoEmAndamento() {
		BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);
		assertDoesNotThrow(() -> validacao.validar(dto));
	}
}