package com.enterprise.tarefas.service;

import com.enterprise.tarefas.enums.Situacao;
import com.enterprise.tarefas.exception.ResourceNotFoundException;
import com.enterprise.tarefas.model.dto.TarefaRequestDTO;
import com.enterprise.tarefas.model.dto.TarefaResponseDTO;
import com.enterprise.tarefas.model.dto.TarefaUpdateDTO;
import com.enterprise.tarefas.model.entity.Tarefa;
import com.enterprise.tarefas.model.mapper.TarefaMapper;
import com.enterprise.tarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTests {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private TarefaMapper tarefaMapper;

    @InjectMocks
    private TarefaService tarefaService;

    private TarefaResponseDTO tarefaResponseDTO;
    private TarefaRequestDTO tarefaRequestDTO;
    private TarefaUpdateDTO tarefaUpdateDTO;
    private Tarefa tarefa;

    @BeforeEach
    void setup() {
        tarefaResponseDTO = TarefaResponseDTO.builder()
                .id(1L)
                .titulo("Tirar o lixo")
                .dataVencimento(LocalDate.now().minusDays(3))
                .situacao(Situacao.PENDENTE)
                .build();

        tarefaRequestDTO = TarefaRequestDTO.builder()
                .titulo("Estudar Spring Security")
                .descricao("Aprender a implementar autenticação por token")
                .dataVencimento(LocalDate.now().plusDays(10))
                .situacao(Situacao.EM_ANDAMENTO)
                .build();

        tarefaUpdateDTO = TarefaUpdateDTO.builder()
                .titulo("Lavar roupa")
                .dataVencimento(LocalDate.now().minusDays(1))
                .situacao(Situacao.CONCLUIDA)
                .build();

        tarefa = Tarefa.builder()
                .id(1L)
                .titulo("Alimentar o gato")
                .descricao("Encher a tigela de comida do Javinha")
                .dataVencimento(LocalDate.now())
                .situacao(Situacao.EM_ANDAMENTO)
                .build();
    }

    @Test
    void testTarefaService_QuandoChamarGetTarefas_DeveRetornarListaDeTarefas() {
        when(tarefaRepository.findAll()).thenReturn(List.of(tarefa));
        when(tarefaMapper.toTarefaResponseDTO(tarefa)).thenReturn(tarefaResponseDTO);

        List<TarefaResponseDTO> tarefas = tarefaService.getTarefas();

        assertThat(tarefas).hasSize(1);
        verify(tarefaRepository).findAll();
        verify(tarefaMapper).toTarefaResponseDTO(tarefa);
    }

    @Test
    void testTarefaService_QuandoChamarCreateTarefa_DeveCriarNovaTarefa() {
        when(tarefaMapper.toTarefa(tarefaRequestDTO)).thenReturn(tarefa);
        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);
        when(tarefaMapper.toTarefaResponseDTO(tarefa)).thenReturn(tarefaResponseDTO);

        var tarefaCriada = tarefaService.createTarefa(tarefaRequestDTO);

        assertThat(tarefaCriada.titulo()).isEqualTo("Tirar o lixo");
        verify(tarefaRepository).save(tarefa);
        verify(tarefaMapper).toTarefa(tarefaRequestDTO);
        verify(tarefaMapper).toTarefaResponseDTO(tarefa);
    }

    @Test
    void testTarefaService_QuandoChamarUpdateTarefa_DeveAtualizarTarefa() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        TarefaResponseDTO respostaAtualizada = TarefaResponseDTO.builder()
                .id(1L)
                .titulo(tarefaUpdateDTO.titulo())
                .dataVencimento(tarefaUpdateDTO.dataVencimento())
                .situacao(tarefaUpdateDTO.situacao())
                .build();

        when(tarefaMapper.toTarefaResponseDTO(tarefa)).thenReturn(respostaAtualizada);

        var tarefaAtualizada = tarefaService.updateTarefa(1L, tarefaUpdateDTO);

        assertThat(tarefaAtualizada.titulo()).isEqualTo(tarefaUpdateDTO.titulo());
        assertThat(tarefaAtualizada.situacao()).isEqualTo(tarefaUpdateDTO.situacao());
        assertThat(tarefaAtualizada.dataVencimento()).isEqualTo(tarefaUpdateDTO.dataVencimento());
        verify(tarefaRepository).findById(1L);
        verify(tarefaRepository).save(tarefa);
        verify(tarefaMapper).toTarefaResponseDTO(tarefa);
    }

    @Test
    void testTarefaService_QuandoChamarDeleteTarefa_DeveDeletarTarefa() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        tarefaService.deleteTarefa(1L);
        verify(tarefaRepository).delete(tarefa);
    }

    @Test
    void testTarefaService_QuandoChamarDeleteTarefaComIdInexistente_DeveLancarResourceNotFoundException() {
        when(tarefaRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tarefaService.deleteTarefa(5L));
    }
}
