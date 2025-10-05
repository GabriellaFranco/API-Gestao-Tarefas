package com.enterprise.tarefas.model.entity;

import com.enterprise.tarefas.enums.Situacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataEntrega;
    private String responsavel;

    @Enumerated(EnumType.STRING)
    Situacao situacao;
}
