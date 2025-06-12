package com.enterprise.tarefas.model.dto;

import com.enterprise.tarefas.enums.Situacao;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TarefaUpdateDTO(
        String titulo,
        LocalDate dataVencimento,
        Situacao situacao
) {}
