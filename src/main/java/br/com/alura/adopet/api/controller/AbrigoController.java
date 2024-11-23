package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.abrigo.DadosCadastroAbrigo;
import br.com.alura.adopet.api.dto.abrigo.AbrigoDto;
import br.com.alura.adopet.api.dto.pet.DadosCadastroPet;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
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

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<AbrigoDto>> listar() {
        return ResponseEntity.ok(abrigoService.listar());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AbrigoDto> cadastrar(@RequestBody @Valid DadosCadastroAbrigo dto) {
        abrigoService.cadastrarAbrigo(dto);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<PetDto>> listarPets(@PathVariable String idOuNome) {
        List<PetDto> petsDoAbrigo = abrigoService.listarPetsDoAbrigo(idOuNome);
        return ResponseEntity.ok(petsDoAbrigo);
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<PetDto> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid DadosCadastroPet dto) {
        Abrigo abrigo = abrigoService.carregarAbrigo(idOuNome);
        PetDto petDto = petService.cadastrarPet(abrigo, dto);
        return ResponseEntity.ok(petDto);

    }

}
