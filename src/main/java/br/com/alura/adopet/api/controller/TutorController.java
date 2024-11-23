package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.tutor.DadosAtualizarTutor;
import br.com.alura.adopet.api.dto.tutor.DadosCadastroTutor;
import br.com.alura.adopet.api.dto.tutor.DadosDetalhesTutor;
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
    public ResponseEntity<DadosDetalhesTutor> cadastrar(@RequestBody @Valid DadosCadastroTutor dto) {
        var detalhesTutor = tutorService.cadastrarTutor(dto);
        return ResponseEntity.ok(detalhesTutor);

    }

    @PutMapping
    @Transactional
    public ResponseEntity<Void> atualizar(@RequestBody @Valid DadosAtualizarTutor dto) {
        tutorService.atualizarTutor(dto);
        return ResponseEntity.ok().build();
    }

}
