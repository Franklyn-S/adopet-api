package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.tutor.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.tutor.CadastroTutorDto;
import br.com.alura.adopet.api.dto.tutor.TutorDto;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @PostMapping
    @Transactional
    public ResponseEntity<TutorDto> cadastrar(@RequestBody @Valid CadastroTutorDto dto) {
        var detalhesTutor = tutorService.cadastrarTutor(dto);
        return ResponseEntity.ok(detalhesTutor);

    }

    @PutMapping
    @Transactional
    public ResponseEntity<Void> atualizar(@RequestBody @Valid AtualizacaoTutorDto dto) {
        tutorService.atualizarTutor(dto);
        return ResponseEntity.ok().build();
    }

}
