package com.enterprise.tarefas.model.dto;

import com.enterprise.tarefas.enums.Situacao;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TarefaUpdateDTO(

        @NotBlank
        @Size(max = 50, message = "O título deve ter no máximo 50 caracteres")
        String titulo,

        @NotNull
        @Future(message = "A data de vencimento deve ser futura")
        LocalDate dataVencimento,

        @NotNull
        Situacao situacao,

        @NotBlank
        @Size(max = 50, message = "O nome do responsável deve ter no máximo 50 caracteres")
        String repsonsavel
) {}
