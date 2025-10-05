package com.enterprise.tarefas.mapper;

import com.enterprise.tarefas.model.dto.TarefaRequestDTO;
import com.enterprise.tarefas.model.dto.TarefaResponseDTO;
import com.enterprise.tarefas.model.dto.TarefaUpdateDTO;
import com.enterprise.tarefas.model.entity.Tarefa;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TarefaMapper {

    public TarefaResponseDTO toTarefaResponseDTO(Tarefa tarefa) {
        return TarefaResponseDTO.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .situacao(tarefa.getSituacao())
                .dataVencimento(tarefa.getDataEntrega())
                .responsavel(tarefa.getResponsavel())
                .build();
    }

    public Tarefa toTarefa(TarefaRequestDTO tarefaDTO) {
        return Tarefa.builder()
                .titulo(tarefaDTO.titulo())
                .descricao(tarefaDTO.descricao())
                .situacao(tarefaDTO.situacao())
                .dataEntrega(tarefaDTO.dataVencimento())
                .responsavel(tarefaDTO.responsavel())
                .build();
    }

    public void updateFromDTO(TarefaUpdateDTO updateDTO, Tarefa tarefaAtual) {
        Optional.ofNullable(updateDTO.titulo()).ifPresent(tarefaAtual::setTitulo);
        Optional.ofNullable(updateDTO.dataVencimento()).ifPresent(tarefaAtual::setDataEntrega);
        Optional.ofNullable(updateDTO.situacao()).ifPresent(tarefaAtual::setSituacao);
        Optional.ofNullable(updateDTO.repsonsavel()).ifPresent(tarefaAtual::setResponsavel);
    }
}
