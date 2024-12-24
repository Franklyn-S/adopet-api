package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
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
class ValidacaoTutorComLimiteDeAdocoesTest {

	@InjectMocks
	private ValidacaoTutorComLimiteDeAdocoes validacao;

	@Mock
	private Tutor tutor;

	@Mock
	private AdocaoRepository adocaoRepository;

	@Mock
	private SolicitacaoAdocaoDto dto;

	@Test
	@DisplayName("Tutor com mais de 5 adoções deve lançar exceção")
	void tutorComMaisDe5Adocoes() {
		Integer qtdAdocoes = 5;
		BDDMockito.given(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO)).willReturn(qtdAdocoes);
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
		assertEquals("Tutor chegou ao limite máximo de 5 adoções!", exception.getMessage());
	}

	@Test
	@DisplayName("Tutor com menos de 5 adoções não deve lançar exceção")
	void tutorComMenosDe5Adocoes() {
		BDDMockito.given(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO)).willReturn(4);
		assertDoesNotThrow(() -> validacao.validar(dto));
	}
}