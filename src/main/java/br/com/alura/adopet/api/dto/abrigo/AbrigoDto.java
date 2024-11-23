package br.com.alura.adopet.api.dto.abrigo;

import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.model.Abrigo;

import java.util.List;

public record AbrigoDto(
		Long id,
		String nome,
		String telefone,
		String email,
		List<PetDto> pets
) {
	public AbrigoDto(Abrigo abrigo) {
		this(
				abrigo.getId(),
				abrigo.getNome(),
				abrigo.getTelefone(),
				abrigo.getEmail(),
				abrigo.getPets().stream().map(PetDto::new).toList()
		);
	}
}
