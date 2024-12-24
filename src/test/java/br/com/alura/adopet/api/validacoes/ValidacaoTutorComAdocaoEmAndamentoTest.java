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
class ValidacaoTutorComAdocaoEmAndamentoTest {

	@InjectMocks
	private ValidacaoTutorComAdocaoEmAndamento validacao;

	@Mock
	private Tutor tutor;

	@Mock
	private AdocaoRepository adocaoRepository;

	@Mock
	private SolicitacaoAdocaoDto dto;

	@Test
	@DisplayName("Tutor com adoção em andamento deve lançar exceção")
	void tutorComAdocaoEmAndamento() {
		BDDMockito.given(adocaoRepository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
		assertEquals("Tutor já possui outra adoção aguardando avaliação!", exception.getMessage());
	}

	@Test
	@DisplayName("Tutor sem adoção em andamento não deve lançar exceção")
	void tutorSemAdocaoEmAndamento() {
		BDDMockito.given(adocaoRepository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);
		assertDoesNotThrow(() -> validacao.validar(dto));
	}
}