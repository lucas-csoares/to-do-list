package com.todolist.unit.service;

import com.todolist.entity.Tarefa;
import com.todolist.repository.TarefaRepository;
import com.todolist.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.todolist.mock.TarefaMock.*;

@ExtendWith (MockitoExtension.class)
@RunWith (JUnitPlatform.class)
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
    private Tarefa tarefaMockComConflito;
    private Tarefa tarefaMockComDataInvalida;

    @BeforeEach
    public void setUp() {
        tarefaMockcomDataPrevisao = obterTarefaMockComDataPrevisao();
        tarefaMockComPrazo = obterTarefaMockComPrazo();
        tarefaMockLivre = obterTarefaMockLivre();
        tarefaMockComConflito = obterTarefaMockComConflito();
        tarefaMockComDataInvalida = obterTarefaMockComDataInvalida();
    }








}
