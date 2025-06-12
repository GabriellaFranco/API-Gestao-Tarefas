package com.enterprise.tarefas.enums;

import com.enterprise.tarefas.exception.ResourceNotFoundException;

public enum Situacao {
    PENDENTE,
    EM_ANDAMENTO,
    CONCLUIDA;

    public Situacao parseSituacaoTarefa(String situacao) {
        try {
            return Situacao.valueOf(situacao.toLowerCase());
        } catch (IllegalArgumentException exc) {
            throw new ResourceNotFoundException("Situação não encontrada no sistema: " + situacao);
        }
    }
}
