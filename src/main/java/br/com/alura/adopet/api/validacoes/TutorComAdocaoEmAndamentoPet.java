package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TutorComAdocaoEmAndamentoPet implements ValidacaoSolicitacaoAdocao {

	@Autowired
	private AdocaoRepository adocaoRepository;

	public void validar(SolicitacaoAdocaoDto dto) {
		Boolean tutorComAdocaoEmAndamento = adocaoRepository
				.existsAdocaoByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO);
		if (tutorComAdocaoEmAndamento) {
			throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
		}
	}
}
