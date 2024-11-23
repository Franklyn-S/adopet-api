package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.abrigo.DadosCadastroAbrigo;
import br.com.alura.adopet.api.service.PetService;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "abrigos")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Abrigo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private String email;

    @OneToMany(mappedBy = "abrigo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("abrigo_pets")
    private List<Pet> pets;

    public Abrigo(DadosCadastroAbrigo dto) {
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.email = dto.email();
        this.pets = dto.pets().stream().map(Pet::new).toList();
    }
}
