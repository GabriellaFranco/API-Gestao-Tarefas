package com.enterprise.tarefas.controller;

import com.enterprise.tarefas.model.dto.TarefaRequestDTO;
import com.enterprise.tarefas.model.dto.TarefaResponseDTO;
import com.enterprise.tarefas.model.dto.TarefaUpdateDTO;
import com.enterprise.tarefas.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    @Operation(
            summary = "Retorna uma lista com todas as tarefas existentes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "204", description = "Nenhuma tarefa para listar")
            }
    )
    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> getTarefas() {
        var tarefas = tarefaService.getTarefas();
        return tarefas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(tarefas);
    }

    @Operation(
            summary = "Cria uma nova tarefa",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sucesso"),
                    @ApiResponse(responseCode = "400", description = "Informações inválidas")
            }
    )
    @PostMapping
    public ResponseEntity<TarefaResponseDTO> createTarefa(@Valid @RequestBody TarefaRequestDTO tarefaDTO) {
        var tarefa = tarefaService.createTarefa(tarefaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tarefa.id()).toUri();
        return ResponseEntity.created(uri).body(tarefa);
    }

    @Operation(
            summary = "Atualiza as informações da tarefa com o id informado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "400", description = "Informações inválidas"),
            }
    )
    @PutMapping("/update-tarefa/{tarefaId}")
    public ResponseEntity<TarefaResponseDTO> updateTarefa(@PathVariable Long tarefaId, @Valid @RequestBody TarefaUpdateDTO tarefaUpdate) {
        var tarefaAtualizada = tarefaService.updateTarefa(tarefaId, tarefaUpdate);
        return ResponseEntity.ok(tarefaAtualizada);
    }

    @Operation(
            summary = "Deleta a tarefa com o id informado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            }
    )
    @DeleteMapping("/delete-tarefa/{tarefaId}")
    public ResponseEntity<String> deleteTarefa(@PathVariable Long tarefaId) {
        tarefaService.deleteTarefa(tarefaId);
        return ResponseEntity.ok("Tarefa deletada com sucesso: " + tarefaId);
    }
}
