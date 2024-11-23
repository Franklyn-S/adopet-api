package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPetDisponivel implements ValidacaoSolicitacaoAdocao {

	@Autowired
	PetRepository petRepository;

	public void validar(SolicitacaoAdocaoDto dto) {
		var pet = petRepository.getReferenceById(dto.idPet());
		if (pet.getAdotado()) {
			throw new ValidacaoException("Pet já foi adotado!");
		}
	}
}
