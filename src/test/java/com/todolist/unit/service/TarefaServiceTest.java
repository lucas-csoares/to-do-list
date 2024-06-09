package com.todolist.unit.service;

import com.todolist.entity.Tarefa;
import com.todolist.enums.PrioridadeTarefa;
import com.todolist.enums.StatusTarefa;
import com.todolist.exceptions.*;
import com.todolist.repository.TarefaRepository;
import com.todolist.request.AtualizarTarefaRequest;
import com.todolist.request.CreateTarefaRequest;
import com.todolist.service.TarefaService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.todolist.mock.TarefaMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
        tarefaMockcomDataPrevisao = tarefaComDataPrevisao2();
        tarefaMockComPrazo = obterTarefaMockComPrazo();
        tarefaMockLivre = obterTarefaMockLivre();

        requestTarefaMockcomDataPrevisao = tarefaComDataPrevisao();
        requestTarefaSemPrioridade = tarefaSemPrioridade();
        requestTarefaMockComConflito = tarefaConflitante();
        requestTarefaMockComDataInvalida = tarefaComDataPrevisaoInferior();

        requestAtualizarTarefaComDataPrevisao = atualizarTarefaComDataPrevisao();
    }


    @Test
    @Order (1)
    void createTarefa_Sucesso() {
        lenient().when(tarefaRepository.save(any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Verifica se nenhuma exceção é lançada
        assertDoesNotThrow(() -> tarefaService.create(requestTarefaMockcomDataPrevisao));
    }

    @Test
    @Order (5)
    @DisplayName("Deve excluir uma tarefa com sucesso")
    void deleteTarefa_Success() {
        String titulo = tarefaMockcomDataPrevisao.getTitulo ();
        Pageable pageable = PageRequest.of (0, 5);
        Page<Tarefa> result = tarefaService.findByTitulo (titulo, pageable);
        Tarefa tarefaResult = result.getContent().get(0);
        Long id = tarefaResult.getId ();

        lenient ().when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefaMockcomDataPrevisao));

        // Chama o método delete com o ID válido
        tarefaService.delete(id);

        // Verifica se o método delete do repositório foi chamado com o ID correto
        verify(tarefaRepository, times(1)).deleteById(id);
    }

    @Test
    @Order (2)
    @DisplayName ("findByTitulo -> Deve retornar a tarefa quando o título existe")
    void findByTitulo_tituloFound_tarefaReturnObject() {

        Pageable pageable = PageRequest.of (0, 5);
        String titulo = tarefaMockcomDataPrevisao.getTitulo ();


        // Simula a página de resultado contendo um único elemento
        Page<Tarefa> page = new PageImpl<> (Collections.singletonList(tarefaMockcomDataPrevisao), pageable, 1);


        // Configura comportamento do repository para simulação
        when(tarefaRepository.findByTituloContainingOrderByDataAtualizacaoDesc(titulo,
                pageable)).thenReturn(page);

        // Testando de fato
        Page<Tarefa> result = tarefaService.findByTitulo (titulo, pageable);

        // Assert: verificação dos resultados
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(0, result.getNumber());
        Tarefa tarefaResult = result.getContent().get(0);

        // Verifica se os atributos da tarefa retornada são iguais aos do mock.
        assertEquals(tarefaMockcomDataPrevisao.getId(), tarefaResult.getId());
        assertEquals(tarefaMockcomDataPrevisao.getTitulo(), tarefaResult.getTitulo());
        assertEquals(tarefaMockcomDataPrevisao.getDataPrevisao(), tarefaResult.getDataPrevisao());

    }




    @Test
    @Order (3)
    void createTarefa_PrevisaoInferiorDataAtual_ThrowsDataInvalidaException() {

        // Configura o comportamento do mock do repositório para aceitar a tarefa sem problemas
        Mockito.
                lenient()
                .when(tarefaRepository.save(Mockito.any(Tarefa.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        assertThrows(DataException.class, () -> tarefaService.create(requestTarefaMockComDataInvalida));
    }


    @Test
    @Order (3)
    void createTarefa_SemPrioridade_ThrowsPrioridadeException() {

        // Verifica se a exceção PrioridadeException é lançada
        assertThrows(PrioridadeException.class, () -> tarefaService.create(requestTarefaSemPrioridade));
    }

    @Test
    @Order (4)
    void createTarefa_ComPrazoEDataPrevisao_ThrowsTarefaComPrazoEDataException() {
        assertThrows(TarefaComPrazoEDataException.class, () -> tarefaService.create(requestTarefaMockComConflito));
    }




    @Test
    @Order (6)
    @DisplayName("Deve lançar exceção ao tentar excluir uma tarefa que não existe")
    void deleteTarefa_NaoEncontrada_ThrowsTarefaNaoEncontradaException() {
        Long id = 999L; // ID de uma tarefa que não existe

        lenient().when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        // Chama o método delete com o ID da tarefa que não existe
        assertThrows(TarefaNaoEncontradaException.class, () -> tarefaService.delete(id));
    }


    @Test
    @Order (7)
    @DisplayName("Deve retornar uma página de tarefas com sucesso")
    void findAllTarefas_Success() {
        // Criação de uma lista de tarefas para simular os dados do banco
        List<Tarefa> tarefas = Arrays.asList(tarefaMockcomDataPrevisao, tarefaMockComPrazo, tarefaMockLivre);

        // Configura o comportamento do mock do repositório para retornar uma página de tarefas
        Page<Tarefa> page = new PageImpl<>(tarefas);
        when(tarefaRepository.findAllByOrderByDataAtualizacaoDesc(any(Pageable.class))).thenReturn(page);

        // Chama o método findAll
        Page<Tarefa> result = tarefaService.findAll(PageRequest.of(0, 5));


        // Verifica se a página retornada não está vazia
        assertFalse(result.isEmpty());

        // Verifica se o número de elementos na página é igual ao número de tarefas simuladas
        assertEquals(tarefas.size(), result.getContent().size());

        // Verifica se os elementos na página são os mesmos da lista simulada
        assertTrue(result.getContent().containsAll(tarefas));
    }


    @Test
    @Order (7)
    @DisplayName("Deve retornar uma página vazia quando não há tarefas")
    void findAllTarefas_EmptyPage() {

        // Configura o comportamento do mock do repositório para retornar uma página vazia
        Page<Tarefa> page = new PageImpl<>(Collections.emptyList());
        when(tarefaRepository.findAllByOrderByDataAtualizacaoDesc(any(Pageable.class))).thenReturn(page);

        // Chama o método findAll
        Page<Tarefa> result = tarefaService.findAll(PageRequest.of(0, 5));

        // Verifica se o método do repositório foi chamado com a paginação correta
        verify(tarefaRepository, times(1)).findAllByOrderByDataAtualizacaoDesc(any(Pageable.class));

        // Verifica se a página retornada está vazia
        assertTrue(result.isEmpty());
    }


    @Test
    @Order (8)
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
    @Order (9)
    void update_TarefaNaoEncontrada_ThrowsTarefaNaoEncontradaException() {
        Long id = 99L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () -> {
            tarefaService.update(id, requestAtualizarTarefaComDataPrevisao);
        });
    }

    @Test
    @Order (9)
    void update_TarefaFinalizada_ThrowsNaoPermitirAtualizarException() {
        Long id = 15L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.of(obterTarefaMockFinalizada()));
        assertThrows(NaoPermitirAtualizarException.class, () -> {
            tarefaService.update(id, requestAtualizarTarefaComDataPrevisao);
        });
    }

    @Test
    @Order (10)
    void update_TarefaComTituloExistente_ThrowsTarefaExistenteException() {
        Long id = tarefaMockcomDataPrevisao.getId();
        Tarefa tarefaComMesmoTitulo = obterTarefaMockComDataPrevisao();
        tarefaComMesmoTitulo.setId(99L); // Outra tarefa com mesmo título

        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefaMockcomDataPrevisao));

        when(tarefaRepository.findByTitulo(requestAtualizarTarefaComDataPrevisao.getTitulo())).thenReturn(tarefaComMesmoTitulo);

        assertThrows(TarefaExistenteException.class, () -> tarefaService.update(id, requestAtualizarTarefaComDataPrevisao));
    }










}
