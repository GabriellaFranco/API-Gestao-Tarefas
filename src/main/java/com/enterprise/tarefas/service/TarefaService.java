package com.enterprise.tarefas.service;

import com.enterprise.tarefas.exception.ResourceNotFoundException;
import com.enterprise.tarefas.model.dto.TarefaRequestDTO;
import com.enterprise.tarefas.model.dto.TarefaResponseDTO;
import com.enterprise.tarefas.model.dto.TarefaUpdateDTO;
import com.enterprise.tarefas.mapper.TarefaMapper;
import com.enterprise.tarefas.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;

    public List<TarefaResponseDTO> getTarefas() {
        return tarefaRepository.findAll().stream().map(tarefaMapper::toTarefaResponseDTO).toList();
    }

    public TarefaResponseDTO getTarefaById(Long id) {
        return tarefaRepository.findById(id).map(tarefaMapper::toTarefaResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada: " + id));
    }

    @Transactional
    public TarefaResponseDTO createTarefa(TarefaRequestDTO tarefaDTO) {
        var tarefaMapeada = tarefaMapper.toTarefa(tarefaDTO);
        var tarefaSalva = tarefaRepository.save(tarefaMapeada);
        return tarefaMapper.toTarefaResponseDTO(tarefaSalva);
    }

    @Transactional
    public TarefaResponseDTO updateTarefa(Long idTarefa, TarefaUpdateDTO tarefaUpdate) {
        var tarefa = tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada: " + idTarefa));

        tarefaMapper.updateFromDTO(tarefaUpdate, tarefa);

        var tarefaAtualizada = tarefaRepository.save(tarefa);
        return tarefaMapper.toTarefaResponseDTO(tarefaAtualizada);
    }

    @Transactional
    public void deleteTarefa(Long idTarefa) {
        var tarefa = tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada: " + idTarefa));
        tarefaRepository.delete(tarefa);
    }
}
