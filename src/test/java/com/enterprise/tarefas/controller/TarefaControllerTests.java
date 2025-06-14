package com.enterprise.tarefas.controller;

import com.enterprise.tarefas.enums.Situacao;
import com.enterprise.tarefas.model.dto.TarefaRequestDTO;
import com.enterprise.tarefas.model.dto.TarefaResponseDTO;
import com.enterprise.tarefas.model.dto.TarefaUpdateDTO;
import com.enterprise.tarefas.service.TarefaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(TarefaController.class)
public class TarefaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TarefaService tarefaService;

    private TarefaRequestDTO tarefaRequestDTO;
    private TarefaResponseDTO tarefaResponseDTO;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        tarefaRequestDTO = TarefaRequestDTO.builder()
                .titulo("Tirar o lixo")
                .descricao("Tirar o lixo da cozinha")
                .dataVencimento(LocalDate.now().plusDays(1))
                .situacao(Situacao.EM_ANDAMENTO)
                .build();

        tarefaResponseDTO = TarefaResponseDTO.builder()
                .id(1L)
                .titulo("Estudar Spring Security")
                .dataVencimento(LocalDate.now().minusDays(5))
                .situacao(Situacao.PENDENTE)
                .build();
    }

    @Test
    void testTarefaController_QuandoChamarGetTarefas_DeveRetornar200EListarAsTarefas() throws Exception {
        when(tarefaService.getTarefas()).thenReturn(List.of(tarefaResponseDTO));
        mockMvc.perform(get("/tarefas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo").value("Estudar Spring Security"));
    }

    @Test
    void testTarefaController_QuandoChamarGetTarefasSemExistirTarefas_DeveRetornar204() throws Exception {
        when(tarefaService.getTarefas()).thenReturn(List.of());
        mockMvc.perform(get("/tarefas"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testTarefaController_QuandoChamarCreateTarefaComDadosValidos_DeveRetornar201ECriarTarefa() throws Exception {
        when(tarefaService.createTarefa(Mockito.any())).thenReturn(tarefaResponseDTO);
        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefaRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));

    }

    @Test
    void testTarefaController_QuandoChamarCreateTarefaComTituloMaiorQue50_DeveRetornar400() throws Exception {
        TarefaRequestDTO invalidRequest = TarefaRequestDTO.builder()
                .titulo("Título muito longo que ultrapassa cinquenta caracteres e invalida o campo")
                .descricao("Teste")
                .dataVencimento(LocalDate.now().plusDays(1))
                .situacao(Situacao.PENDENTE)
                .build();

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        Mockito.verify(tarefaService, Mockito.never()).createTarefa(Mockito.any());
    }

    @Test
    void testTarefaController_QuandoChamarCreateTarefaComDataVencimentoNoPassado_DeveRetornar400() throws Exception {
        tarefaRequestDTO = TarefaRequestDTO.builder()
                .titulo("Tarefa 1")
                .descricao("Teste")
                .situacao(Situacao.PENDENTE)
                .dataVencimento(LocalDate.now().minusDays(1))
                .build();

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefaRequestDTO)))
                .andExpect(status().isBadRequest());

        Mockito.verify(tarefaService, Mockito.never()).createTarefa(Mockito.any());

    }

    @Test
    void testTarefaController_QuandoCriarTarefaComSituacaoInvalida_DeveRetornar400() throws Exception {
        String json = """
                {
                  "titulo": "Título válido",
                  "descricao": "Descrição válida",
                  "dataVencimento": "%s",
                  "situacao": "INVALIDA"
                }
                """.formatted(LocalDate.now().plusDays(1));

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testTarefaController_QuandoChamarUpdateTarefa_DeveAtualizarTarefaComSucesso() throws Exception {
        var tarefaUpdateDTO = TarefaUpdateDTO.builder()
                .titulo("Título atualizado")
                .dataVencimento(LocalDate.now().plusDays(20))
                .situacao(Situacao.CONCLUIDA)
                .build();

        var tarefaAtualizada = TarefaResponseDTO.builder()
                .id(1L)
                .titulo(tarefaUpdateDTO.titulo())
                .dataVencimento(tarefaUpdateDTO.dataVencimento())
                .situacao(tarefaUpdateDTO.situacao())
                .build();

        when(tarefaService.updateTarefa(eq(1L), Mockito.any())).thenReturn(tarefaAtualizada);
        mockMvc.perform(put("/tarefas/update-tarefa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefaAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Título atualizado"));
    }

    @Test
    void testTarefaController_QuandoChamarDeleteTarefa_DeveRetornar200() throws Exception {
        mockMvc.perform(delete("/tarefas/delete-tarefa/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Tarefa deletada com sucesso")));
    }
}
