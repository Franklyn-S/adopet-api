package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.abrigo.DadosCadastroAbrigo;
import br.com.alura.adopet.api.dto.abrigo.DadosDetalhesAbrigo;
import br.com.alura.adopet.api.dto.pet.DadosCadastroPet;
import br.com.alura.adopet.api.dto.pet.DadosDetalhesPet;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Autowired
    private AbrigoService abrigoService;

    @GetMapping
    public ResponseEntity<List<Abrigo>> listar() {
        return ResponseEntity.ok(abrigoRepository.findAll());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhesAbrigo> cadastrar(@RequestBody @Valid DadosCadastroAbrigo dto) {
        abrigoService.cadastrarAbrigo(dto);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<DadosDetalhesPet>> listarPets(@PathVariable String idOuNome) {
        try {
            List<DadosDetalhesPet> petsDoAbrigo = abrigoService.listarPetsDoAbrigoPorIdOuNome(idOuNome);
            return ResponseEntity.ok(petsDoAbrigo);
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<DadosDetalhesPet> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid DadosCadastroPet dto) {
        DadosDetalhesPet pet = abrigoService.cadastrarPetNoAbrigo(dto, idOuNome);
        return ResponseEntity.ok(pet);

    }

}
