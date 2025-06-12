package com.enterprise.tarefas.model.dto;

import com.enterprise.tarefas.enums.Situacao;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TarefaRequestDTO(

        @NotNull @NotEmpty
        @Size(max = 50)
        String titulo,

        @NotNull @NotEmpty
        @Size(max = 150)
        String descricao,

        @NotNull
        @Future
        LocalDate dataVencimento,

        @NotNull
        Situacao situacao
) {
}
