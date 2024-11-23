package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.tutor.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.tutor.CadastroTutorDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tutores")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private String email;

    @OneToMany(mappedBy = "tutor")
    @JsonManagedReference("tutor_adocoes")
    private List<Adocao> adocoes;

    public Tutor(CadastroTutorDto dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
    }

    public void atualizar(AtualizacaoTutorDto dto) {
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.email = dto.email();
    }
}
