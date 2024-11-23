package br.com.alura.adopet.api.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "adocoes")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tutor tutor;

    @OneToOne(fetch = FetchType.LAZY)
    private Pet pet;

    private String motivo;

    @Enumerated(EnumType.STRING)
    private StatusAdocao status;

    private String justificativaStatus;

    public Adocao(Pet pet, Tutor tutor, String motivo) {
        this.pet = pet;
        this.tutor = tutor;
        this.motivo = motivo;
        this.data = LocalDateTime.now();
        this.status = StatusAdocao.AGUARDANDO_AVALIACAO;
    }

    public void marcarComoReprovado(String justificativa) {
        this.status = StatusAdocao.REPROVADO;
        this.justificativaStatus = justificativa;
    }

    public void marcarComoAprovado() {
        this.status = StatusAdocao.APROVADO;
    }
}
