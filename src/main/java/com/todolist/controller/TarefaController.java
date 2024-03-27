package com.todolist.controller;

import com.todolist.entity.Tarefa;
import com.todolist.request.CreateTarefaRequest;
import com.todolist.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;


    @PostMapping
    public ResponseEntity<Tarefa> create(@Valid @RequestBody CreateTarefaRequest request) {
        Tarefa tarefaSave = tarefaService.create (request);

        return new ResponseEntity<> (tarefaSave, HttpStatus.CREATED);
    }

}
