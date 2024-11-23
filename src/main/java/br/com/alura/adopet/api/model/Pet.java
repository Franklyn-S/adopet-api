package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.pet.DadosCadastroPet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "pets")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPet tipo;

    private String nome;

    private String raca;

    private Integer idade;

    private String cor;

    private Float peso;

    private Boolean adotado;

    @ManyToOne
    @JsonBackReference("abrigo_pets")
    @JoinColumn(name = "abrigo_id")
    private Abrigo abrigo;

    @OneToOne(mappedBy = "pet")
    @JsonBackReference("adocao_pets")
    private Adocao adocao;

    public Pet(DadosCadastroPet dto) {
        this.tipo = dto.tipo();
        this.nome = dto.nome();
        this.raca = dto.raca();
        this.idade = dto.idade();
        this.cor = dto.cor();
        this.peso = dto.peso();
    }

    public Pet(DadosCadastroPet dto, Abrigo abrigo) {
        this(dto);
        this.abrigo = abrigo;
        this.adotado = false;
    }
}


