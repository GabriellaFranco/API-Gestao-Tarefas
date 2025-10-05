package com.enterprise.tarefas.model.dto;

import com.enterprise.tarefas.enums.Situacao;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TarefaRequestDTO(

        @NotBlank
        @Size(max = 50, message = "O título deve ter no máximo 50 caracteres")
        String titulo,

       @NotBlank
        @Size(max = 250, message = "A descrição deve ter no máximo 250 caracteres")
        String descricao,

        @NotNull
        @Future(message = "A data de vencimento deve ser futura")
        LocalDate dataVencimento,

        @NotNull
        Situacao situacao,

        @NotBlank
        @Size(max = 50, message = "O nome do responsável deve ter no máximo 50 caracteres")
        String repsonsavel

) {
}
