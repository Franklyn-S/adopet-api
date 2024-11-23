package br.com.alura.adopet.api.dto.abrigo;

import br.com.alura.adopet.api.dto.pet.DadosCadastroPet;
import br.com.alura.adopet.api.dto.pet.DadosDetalhesPet;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.TipoPet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DadosDetalhesAbrigo(
		Long id,
		String nome,
		String telefone,
		String email,
		List<DadosDetalhesPet> pets
) {
	public DadosDetalhesAbrigo(Abrigo abrigo) {
		this(
				abrigo.getId(),
				abrigo.getNome(),
				abrigo.getTelefone(),
				abrigo.getEmail(),
				abrigo.getPets().stream().map(DadosDetalhesPet::new).toList()
		);
	}
}
