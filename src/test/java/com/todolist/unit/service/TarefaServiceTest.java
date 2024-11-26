package com.todolist.unit.service;

import com.todolist.entity.Tarefa;
import com.todolist.exceptions.*;
import com.todolist.repository.TarefaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.todolist.request.AtualizarTarefaRequest;
import com.todolist.request.CreateTarefaRequest;
import com.todolist.service.TarefaService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static com.todolist.mock.TarefaMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @InjectMocks
    private TarefaService tarefaService;
    @Mock
    private TarefaRepository tarefaRepository;

    private Tarefa tarefaMockcomDataPrevisao;

    private Tarefa tarefaMockComPrazo;
    private Tarefa tarefaMockLivre;

    private CreateTarefaRequest requestTarefaMockcomDataPrevisao;
    private CreateTarefaRequest requestTarefaMockComConflito;
    private CreateTarefaRequest requestTarefaMockComDataInvalida;
    private CreateTarefaRequest requestTarefaSemPrioridade;

    private AtualizarTarefaRequest requestAtualizarTarefaComDataPrevisao;


    @BeforeEach
    public void setUp() {
        tarefaMockcomDataPrevisao = obterTarefaMockComDataPrevisao();
        tarefaMockComPrazo = obterTarefaMockComPrazo();
        tarefaMockLivre = obterTarefaMockLivre();
        requestTarefaMockcomDataPrevisao = tarefaComDataPrevisao();
        requestTarefaSemPrioridade = tarefaSemPrioridade();
        requestTarefaMockComConflito = tarefaConflitante();
        requestTarefaMockComDataInvalida = tarefaComDataPrevisaoInferior();
        requestAtualizarTarefaComDataPrevisao = atualizarTarefaComDataPrevisao();
    }


    @Test
    @DisplayName("Criar tarefa com sucesso")
    void createTarefa_Sucesso() {
        lenient().when(tarefaRepository.save(any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> tarefaService.create(requestTarefaMockcomDataPrevisao));
    }

    @Test
    @DisplayName("Criar tarefa com data de previsão inferior à atual -> lança DataInvalidaException")
    void createTarefa_PrevisaoInferiorDataAtual_ThrowsDataInvalidaException() {

        // Configura o comportamento do mock do repositório para aceitar a tarefa sem problemas
        Mockito.
                lenient()
                .when(tarefaRepository.save(Mockito.any(Tarefa.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        assertThrows(DataException.class, () -> tarefaService.create(requestTarefaMockComDataInvalida));
    }


    @Test
    @DisplayName("Criar tarefa sem prioridade lança PrioridadeException")
    void createTarefa_SemPrioridade_ThrowsPrioridadeException() {

        // Verifica se a exceção PrioridadeException é lançada
        assertThrows(PrioridadeException.class, () -> tarefaService.create(requestTarefaSemPrioridade));
    }

    @Test
    @DisplayName("Criar tarefa com conflito de prazo e data lança TarefaComPrazoEDataException")
    void createTarefa_ComPrazoEDataPrevisao_ThrowsTarefaComPrazoEDataException() {
        assertThrows(TarefaComPrazoEDataException.class, () -> tarefaService.create(requestTarefaMockComConflito));
    }

    @Test
    @DisplayName("Deletar tarefa não encontrada lança TarefaNaoEncontradaException")
    void delete_taskNotFound_throwsTarefaNaoEncontradaException() {
        Long tarefaId = 1L;

        lenient().when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () -> tarefaService.delete(tarefaId));
    }

    @Test
    @DisplayName("Deletar tarefa encontrada com sucesso")
    void delete_taskFound_success() {
        Long tarefaId = tarefaMockcomDataPrevisao.getId ();

        lenient ().when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefaMockcomDataPrevisao));

        // Simula o método deleteById do repository
        doNothing().when(tarefaRepository).deleteById(tarefaId);

        assertDoesNotThrow(() -> tarefaService.delete(tarefaId));

        verify(tarefaRepository, times(1)).findById(tarefaId);

        verify(tarefaRepository, times(1)).deleteById(tarefaId);
    }


    @Test
    @DisplayName("Encontrar tarefa pelo título retorna objeto Tarefa")
    void findByTitulo_tituloFound_tarefaReturnObject() {

        Pageable pageable = PageRequest.of (0, 5);
        String titulo = tarefaMockcomDataPrevisao.getTitulo ();

        Page<Tarefa> page = new PageImpl<> (Collections.singletonList(tarefaMockcomDataPrevisao), pageable, 1);


        // Configura comportamento do repository para simulação
        when(tarefaRepository.findByTituloContainingOrderByDataAtualizacaoDesc(titulo,
                pageable)).thenReturn(page);


        Page<Tarefa> result = tarefaService.findByTitulo (titulo, pageable);


        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(0, result.getNumber());
        Tarefa tarefaResult = result.getContent().get(0);

        assertEquals(tarefaMockcomDataPrevisao.getId(), tarefaResult.getId());
        assertEquals(tarefaMockcomDataPrevisao.getTitulo(), tarefaResult.getTitulo());
        assertEquals(tarefaMockcomDataPrevisao.getDataPrevisao(), tarefaResult.getDataPrevisao());

    }








   @Test
   @DisplayName("Encontrar todas as tarefas retorna página de tarefas com sucesso")
    void findAllTarefas_Success() {
        // Criação de uma lista de tarefas para simular os dados do banco
        List<Tarefa> tarefas = Arrays.asList(tarefaMockcomDataPrevisao, tarefaMockComPrazo, tarefaMockLivre);


        Page<Tarefa> page = new PageImpl<> (tarefas);

        when(tarefaRepository.findAllByOrderByDataAtualizacaoDesc(any(Pageable.class))).thenReturn(page);


        Page<Tarefa> result = tarefaService.findAll(PageRequest.of(0, 5));


        // Verifica se a página retornada não está vazia
        assertFalse(result.isEmpty());

        // Verifica se o número de elementos na página é igual ao número de tarefas simuladas
        assertEquals(tarefas.size(), result.getContent().size());

        // Verifica se os elementos na página são os mesmos da lista simulada
        assertTrue(result.getContent().containsAll(tarefas));
    }


    @Test
    @DisplayName("Encontrar todas as tarefas retorna página vazia quando não há tarefas")
    void findAllTarefas_EmptyPage() {

        Page<Tarefa> page = new PageImpl<>(Collections.emptyList());
        when(tarefaRepository.findAllByOrderByDataAtualizacaoDesc(any(Pageable.class))).thenReturn(page);


        Page<Tarefa> result = tarefaService.findAll(PageRequest.of(0, 5));

        // Verifica se o método do repositório foi chamado com a paginação correta
        verify(tarefaRepository, times(1)).findAllByOrderByDataAtualizacaoDesc(any(Pageable.class));

        assertTrue(result.isEmpty());
    }


    @Test
    @DisplayName("Atualizar tarefa existente com sucesso")
    void update_TarefaComDataPrevisao_Success() {
        Long id = tarefaMockcomDataPrevisao.getId ();

        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefaMockcomDataPrevisao));

        when(tarefaRepository.save(Mockito.any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tarefa updatedTarefa = tarefaService.update(id, requestAtualizarTarefaComDataPrevisao);

        assertNotNull(updatedTarefa);
        assertEquals(requestAtualizarTarefaComDataPrevisao.getTitulo(), updatedTarefa.getTitulo());

        assertEquals(requestAtualizarTarefaComDataPrevisao.getDataPrevisao(), updatedTarefa.getDataPrevisao());

        assertEquals(requestAtualizarTarefaComDataPrevisao.getPrioridade(), updatedTarefa.getPrioridade());
    }


    @Test
    @DisplayName("Atualizar tarefa não encontrada lança TarefaNaoEncontradaException")
    void update_TarefaNaoEncontrada_ThrowsTarefaNaoEncontradaException() {
        Long id = 99L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () -> {
            tarefaService.update(id, requestAtualizarTarefaComDataPrevisao);
        });
    }


    @Test
    @DisplayName("Atualizar tarefa finalizada lança NaoPermitirAtualizarException")
    void update_TarefaFinalizada_ThrowsNaoPermitirAtualizarException() {
        Long id = obterTarefaMockFinalizada ().getId ();
        when(tarefaRepository.findById(id)).thenReturn(Optional.of(obterTarefaMockFinalizada()));
        assertThrows(NaoPermitirAtualizarException.class, () -> {
            tarefaService.update(id, requestAtualizarTarefaComDataPrevisao);
        });
    }

    @Test
    @DisplayName("Atualizar tarefa com título existente lança TarefaExistenteException")
    void update_TarefaComTituloExistente_ThrowsTarefaExistenteException() {
        Long id = tarefaMockcomDataPrevisao.getId();


        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefaMockcomDataPrevisao));

        when(tarefaRepository.findByTitulo(requestAtualizarTarefaComDataPrevisao.getTitulo())).thenReturn(tarefaMockLivre);

        assertThrows(TarefaExistenteException.class, () -> tarefaService.update(id, requestAtualizarTarefaComDataPrevisao));
    }










}
