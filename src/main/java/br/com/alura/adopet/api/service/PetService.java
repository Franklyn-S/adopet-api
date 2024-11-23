package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.pet.DadosCadastroPet;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

	@Autowired
	private PetRepository repository;

	@Autowired
	private AbrigoRepository abrigoRepository;

	public List<PetDto> buscarPetsDisponiveis() {
		return repository
				.findAllByAdotadoFalse()
				.stream()
				.map(PetDto::new)
				.toList();

	}

	public PetDto cadastrarPet(Abrigo abrigo, DadosCadastroPet dto) {
		Pet pet = new Pet(dto, abrigo);
		abrigo.getPets().add(pet);
		abrigoRepository.save(abrigo);
		return new PetDto(pet);
	}
}
