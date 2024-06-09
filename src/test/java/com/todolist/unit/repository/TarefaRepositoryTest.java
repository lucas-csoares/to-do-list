package com.todolist.unit.repository;

import com.todolist.entity.Tarefa;
import com.todolist.mock.TarefaMock;
import com.todolist.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository tarefaRepository;

    private Tarefa tarefaMock;

    @BeforeEach
    public void setUp() {
        tarefaMock = TarefaMock.obterTarefaMockComDataPrevisao ();
    }


/*    @Test
    @DisplayName("findAll: no tasks found -> return empty list")
    void findAll_notTasksFound_returnEmptyList() {

        List<Tarefa> tarefasEncontradas = tarefaRepository.findAll();


        assertTrue(tarefasEncontradas.isEmpty());
    }*/



    @Test
    @DisplayName("findById: Encontrar objeto Tarefa")
    void findById_taskFound_returnTaskObject() {

        Tarefa tarefaSalva = tarefaRepository.save (tarefaMock);

        Optional<Tarefa> tarefaEncontrada = tarefaRepository.findById(tarefaSalva.getId ());

        assertNotNull(tarefaEncontrada);
        assertEquals(tarefaSalva.getId (), tarefaEncontrada.get().getId());
    }


}
