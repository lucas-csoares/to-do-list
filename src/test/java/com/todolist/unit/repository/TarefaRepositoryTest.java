package com.todolist.unit.repository;

import com.todolist.entity.Tarefa;
import com.todolist.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import static com.todolist.mock.TarefaMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository tarefaRepository;

    private static int NUMERO_TAREFAS;

    private Tarefa tarefaMockSemId;

    private Tarefa tarefaMockComDataPrevisao;

    private Tarefa tarefaMockComPrazo;


    private Tarefa tarefaMockLivre;

    @BeforeEach
    public void setUp() {

        tarefaMockSemId = obterTarefaMockSemId();
        tarefaMockComDataPrevisao = obterTarefaMockComDataPrevisao ();
        tarefaMockComPrazo = obterTarefaMockComPrazo();
        tarefaMockLivre = obterTarefaMockLivre ();
    }


    @Test
    @DisplayName("findById: Encontrar objeto Tarefa")
    void findById_taskFound_returnTaskObject() {
        Tarefa tarefaSalva = tarefaRepository.save(tarefaMockSemId);
        Optional<Tarefa> tarefaEncontrada = tarefaRepository.findById(tarefaSalva.getId());

        assertTrue(tarefaEncontrada.isPresent()); // Verifica se o Optional contém um valor

        assertEquals(tarefaSalva.getId(), tarefaEncontrada.get().getId());
    }


    @Test
    @DisplayName("findById: tarefa inexistente")
    void findById_noTaskFound_returnEmptyList() {
        Optional<Tarefa> tarefaEncontrada = tarefaRepository.findById(tarefaMockComDataPrevisao.getId());

        assertFalse(tarefaEncontrada.isPresent());
    }

    @Test
    @DisplayName("findAll: retorna objetos tarefa")
    void findAll_tasksFound_returnTasksObjects() {
        NUMERO_TAREFAS = 4;
        salvarTarefas(NUMERO_TAREFAS);

        List<Tarefa> tarefasEncontradas = tarefaRepository.findAll();

        assertNotNull(tarefasEncontradas);
        assertEquals(NUMERO_TAREFAS, tarefasEncontradas.size());

    }


    @Test
    @DisplayName("save: retorna tarefa salva")
    void save_taskFound_returnTaskObject() {
        Tarefa tarefaSalva = tarefaRepository.save(tarefaMockSemId);

        assertNotNull(tarefaSalva);
        assertNotNull(tarefaSalva.getId());
    }


    @Test
    @DisplayName("delete: deletar tarefa com sucesso")
    void delete_taskDeleted_returnTaskObject() {
        NUMERO_TAREFAS = 1;
        salvarTarefas (NUMERO_TAREFAS);

        Tarefa tarefaSalva = tarefaRepository.findAll().get(0);

        tarefaRepository.deleteById(tarefaSalva.getId());

        assertEquals(0, tarefaRepository.findAll().size());
    }



    @Test
    @DisplayName("findByTitulo: retorna objeto tarefa dado um título")
    void testFindByTitulo() {
        Tarefa tarefaSalva = tarefaRepository.save(tarefaMockComPrazo);

        Tarefa tarefaEncontrada = tarefaRepository.findByTitulo(tarefaSalva.getTitulo ());

        assertNotNull(tarefaEncontrada);
        assertEquals("Tarefa com prazo", tarefaEncontrada.getTitulo());
    }




    @Test
    @DisplayName("findByTituloContainingOrderByDataAtualizacaoDesc: retorna um Page de tarefas com apenas um elemento" +
            " com o título equivalente ao passado como argumento")
    void testFindByTituloContainingOrderByDataAtualizacaoDesc() {

        Tarefa tarefaSalva = tarefaRepository.save (tarefaMockLivre);

        Page<Tarefa> tarefasEncontradas =
                tarefaRepository.findByTituloContainingOrderByDataAtualizacaoDesc(tarefaSalva.getTitulo (),
                PageRequest.of(0, 10));

        assertNotNull(tarefasEncontradas);
        assertEquals(1, tarefasEncontradas.getTotalElements());
        assertEquals(tarefaSalva.getTitulo (), tarefasEncontradas.getContent().get(0).getTitulo());

    }


    private void salvarTarefas(int numTarefas) {
        IntStream.range(0, numTarefas)
                .forEach(i -> {
                    Tarefa tarefa = Tarefa.builder()
                            .titulo(tarefaMockSemId.getTitulo())
                            .status(tarefaMockSemId.getStatus())
                            .dataInicio(tarefaMockSemId.getDataInicio())
                            .dataFim(tarefaMockSemId.getDataFim())
                            .dataPrevisao(tarefaMockSemId.getDataPrevisao())
                            .prazo(tarefaMockSemId.getPrazo())
                            .prioridade(tarefaMockSemId.getPrioridade())
                            .build();
                    tarefaRepository.save(tarefa);
                });
    }





}
