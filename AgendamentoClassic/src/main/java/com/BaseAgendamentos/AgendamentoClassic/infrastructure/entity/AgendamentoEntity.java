package com.BaseAgendamentos.AgendamentoClassic.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "agendamento")
public class AgendamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String servico;

    @Column(nullable = false)
    private String profissional;

    @Column(nullable = false)
    private String cliente;

    @Pattern(
        regexp = "^\\(\\d{2}\\)\\s?9?\\d{4}-?\\d{4}$",
        message = "Telefone inválido. Use o formato (XX) 9XXXX-XXXX ou (XX) XXXX-XXXX"
    )
    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private LocalDateTime dataHoraAgendamento;

    @Column(nullable = false)
    private LocalDateTime dataInsercao = LocalDateTime.now();
}
