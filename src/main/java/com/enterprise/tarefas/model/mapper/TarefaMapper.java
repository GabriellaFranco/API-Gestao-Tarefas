package com.enterprise.tarefas.model.mapper;

import com.enterprise.tarefas.model.dto.TarefaRequestDTO;
import com.enterprise.tarefas.model.dto.TarefaResponseDTO;
import com.enterprise.tarefas.model.entity.Tarefa;
import org.springframework.stereotype.Component;

@Component
public class TarefaMapper {

    public TarefaResponseDTO toTarefaResponseDTO(Tarefa tarefa) {
        return TarefaResponseDTO.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .situacao(tarefa.getSituacao())
                .dataVencimento(tarefa.getDataVencimento())
                .build();
    }

    public Tarefa toTarefa(TarefaRequestDTO tarefaDTO) {
        return Tarefa.builder()
                .titulo(tarefaDTO.titulo())
                .descricao(tarefaDTO.descricao())
                .situacao(tarefaDTO.situacao())
                .dataVencimento(tarefaDTO.dataVencimento())
                .build();
    }
}
