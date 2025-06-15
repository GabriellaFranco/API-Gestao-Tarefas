package com.enterprise.tarefas.model.dto;

import com.enterprise.tarefas.enums.Situacao;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TarefaRequestDTO(

        @NotNull @NotEmpty
        @Size(max = 50, message = "O título deve ter no máximo 50 caracteres")
        String titulo,

        @NotNull @NotEmpty
        @Size(max = 250, message = "A descrição deve ter no máximo 250 caracteres")
        String descricao,

        @NotNull
        @Future(message = "A data de vencimento deve ser futura")
        LocalDate dataVencimento,

        @NotNull
        Situacao situacao
) {
}
