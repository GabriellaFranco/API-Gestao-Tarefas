package com.enterprise.tarefas.model.dto;

import com.enterprise.tarefas.enums.Situacao;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TarefaResponseDTO (
        Long id,
        String titulo,
        LocalDate dataVencimento,
        Situacao situacao,
        String responsavel
){}
