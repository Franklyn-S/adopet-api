package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigo.DadosCadastroAbrigo;
import br.com.alura.adopet.api.dto.pet.DadosCadastroPet;
import br.com.alura.adopet.api.dto.pet.DadosDetalhesPet;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {

	@Autowired
	AbrigoRepository abrigoRepository;

	public void cadastrarAbrigo(DadosCadastroAbrigo dto) {
		boolean nomeJaCadastrado = abrigoRepository.existsByNome(dto.nome());
		boolean telefoneJaCadastrado = abrigoRepository.existsByTelefone(dto.telefone());
		boolean emailJaCadastrado = abrigoRepository.existsByEmail(dto.email());

		if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
			throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
		}
		var abrigo = new Abrigo(dto);
		abrigoRepository.save(abrigo);
	}

	public List<DadosDetalhesPet> listarPetsDoAbrigoPorIdOuNome(String idOuNome) throws EntityNotFoundException {
		try {
			Long id = Long.parseLong(idOuNome);
			List<Pet> pets = abrigoRepository.getReferenceById(id).getPets();
			return pets.stream().map(DadosDetalhesPet::new).toList();
		} catch (NumberFormatException e) {
			List<Pet> pets = abrigoRepository.findByNome(idOuNome).getPets();
			return pets.stream().map(DadosDetalhesPet::new).toList();
		}
	}

	public DadosDetalhesPet cadastrarPetNoAbrigo(DadosCadastroPet dto, String idOuNome){
		try {
			Long id = Long.parseLong(idOuNome);
			Abrigo abrigo = abrigoRepository.getReferenceById(id);
			Pet pet = new Pet(dto, abrigo);
			abrigo.getPets().add(pet);
			abrigoRepository.save(abrigo);
			return new DadosDetalhesPet(pet);
		} catch (NumberFormatException nfe) {
			Abrigo abrigo = abrigoRepository.findByNome(idOuNome);
			Pet pet = new Pet(dto, abrigo);
			abrigo.getPets().add(pet);
			abrigoRepository.save(abrigo);
			return new DadosDetalhesPet(pet);
		}
	}
}
