package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutor.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.tutor.CadastroTutorDto;
import br.com.alura.adopet.api.dto.tutor.TutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

	@Autowired
	private TutorRepository repository;

	public TutorDto cadastrarTutor(CadastroTutorDto dto) {
		boolean dadosJaCadastrados = repository.existsByTelefoneOrEmail(dto.telefone(), dto.email());
		if (dadosJaCadastrados) {
			throw new ValidacaoException("Dados já cadastrados para outro tutor!");
		}
		Tutor tutor = new Tutor(dto);
		repository.save(tutor);
		return new TutorDto(tutor);
	}

	public void atualizarTutor(AtualizacaoTutorDto dto) {
		Tutor tutor = repository.getReferenceById(dto.id());
		tutor.atualizar(dto);
	}
}
