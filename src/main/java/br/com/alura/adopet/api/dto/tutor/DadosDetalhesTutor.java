package br.com.alura.adopet.api.dto.tutor;

import br.com.alura.adopet.api.model.Tutor;

public record DadosDetalhesTutor(
		Long id,
		String nome,
		String telefone,
		String email
) {
	public DadosDetalhesTutor(Tutor tutor) {
		this(
				tutor.getId(),
				tutor.getNome(),
				tutor.getTelefone(),
				tutor.getEmail()
		);
	}
}
