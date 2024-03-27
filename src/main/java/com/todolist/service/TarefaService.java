package com.todolist.service;


import com.todolist.entity.Tarefa;
import com.todolist.repository.TarefaRepository;
import com.todolist.request.CreateTarefaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;


    public Tarefa create(CreateTarefaRequest request) {
        Tarefa tarefa = Tarefa.builder ()
                .status (request.getStatusTarefa ())
                .titulo (request.getTitulo ())
                .descricao (request.getDescricao ())
                .build ();

        return this.tarefaRepository.save (tarefa);
    }
}
