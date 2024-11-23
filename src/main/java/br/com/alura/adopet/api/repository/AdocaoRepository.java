package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

	@Query
	Boolean existsAdocaoByPetIdAndStatus(Long petId, StatusAdocao statusAdocao);

	Boolean existsAdocaoByTutorIdAndStatus(Long tutorId, StatusAdocao statusAdocao);

	Integer countByTutorIdAndStatus(Long aLong, StatusAdocao statusAdocao);
}
