package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutor.DadosAtualizarTutor;
import br.com.alura.adopet.api.dto.tutor.DadosCadastroTutor;
import br.com.alura.adopet.api.dto.tutor.DadosDetalhesTutor;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {
	@Autowired
	private TutorRepository repository;

	public DadosDetalhesTutor cadastrarTutor(DadosCadastroTutor dto) {
		Tutor tutor = new Tutor(dto);
		boolean telefoneJaCadastrado = repository.existsByTelefone(tutor.getTelefone());
		boolean emailJaCadastrado = repository.existsByEmail(tutor.getEmail());

		if (telefoneJaCadastrado || emailJaCadastrado) {
			throw new ValidacaoException("Dados já cadastrados para outro tutor!");
		} else {
			repository.save(tutor);
			return new DadosDetalhesTutor(tutor);
		}
	}

	public void atualizarTutor(DadosAtualizarTutor dto) {
		Tutor tutor = repository.getReferenceById(dto.id());
		if (tutor == null) {
			throw new ValidacaoException("Nenhum tutor encontrado!");
		}
		tutor.atualizar(dto);
		repository.save(tutor);
	}
}
