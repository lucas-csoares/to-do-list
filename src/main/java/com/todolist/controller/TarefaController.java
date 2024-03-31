package com.todolist.controller;

import com.todolist.entity.Tarefa;
import com.todolist.request.AtualizarStatusTarefaRequest;
import com.todolist.request.AtualizarTarefaRequest;
import com.todolist.request.CreateTarefaRequest;
import com.todolist.response.AtualizarStatusTarefaResponse;
import com.todolist.response.AtualizarTarefaResponse;
import com.todolist.response.ObterTarefaResponse;
import com.todolist.response.ObterTarefasPaginadasResponse;
import com.todolist.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;


    @PostMapping
    @Operation(summary = "Cadastra uma tarefa na base de dados")
    public ResponseEntity<Tarefa> create(@RequestBody CreateTarefaRequest request) {
        Tarefa tarefaSave = tarefaService.create (request);

        return new ResponseEntity<> (tarefaSave, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Se não for enviado nenhum parâmetro via url, lista todas as tarefas cadastradas. Caso " +
            "contrário, lista as tarefas cadastradas por título.")
    public ResponseEntity<ObterTarefasPaginadasResponse> findByTarefa(
            @RequestParam(required = false) String titulo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){


        Page<Tarefa> tarefasPaginadas;

        if(titulo == null) {
            tarefasPaginadas = this.tarefaService.findAll (PageRequest.of (page, size));
        } else {
            tarefasPaginadas = this.tarefaService.findByTitulo (titulo, PageRequest.of (page, size));
        }

        List<ObterTarefaResponse> tarefas = tarefasPaginadas
                .getContent ()
                .stream ()
                .map (tarefa -> {
                    return ObterTarefaResponse
                            .builder ()
                            .id (tarefa.getId ())
                            .titulo (tarefa.getTitulo ())
                            .descricao (tarefa.getDescricao ())
                            .status (tarefa.getStatus ())
                            .dataInicio (tarefa.getDataInicio().format (DateTimeFormatter.ofPattern ("dd/MM/yyyy")))
                            .dataFim (tarefa.getDataFim () != null ?
                                    tarefa.getDataFim ().format (DateTimeFormatter.ofPattern (("dd/MM/yyyy"))) : null)
                            .build ();
                })
                .toList ();


        ObterTarefasPaginadasResponse response = ObterTarefasPaginadasResponse.builder ()
                .paginaAtual (tarefasPaginadas.getNumber ())
                .totalPaginas (tarefasPaginadas.getTotalPages ())
                .totalItens (tarefasPaginadas.getTotalElements ())
                .tarefas (tarefas)
                .build ();


        return new ResponseEntity<> (response, HttpStatus.OK);


    }


    @PutMapping("/{id}/completed")
    @Operation(summary = "Atualiza o status de uma tarefa para finalizada")
    public ResponseEntity<AtualizarStatusTarefaResponse> updateStatus(@PathVariable Long id,
                                                                      @RequestBody AtualizarStatusTarefaRequest request) {

        Tarefa statusTarefaAtualizada = tarefaService.updateStatus (id, request);

        AtualizarStatusTarefaResponse atualizarStatusTarefaResponse = AtualizarStatusTarefaResponse
                .builder ()
                .status (statusTarefaAtualizada.getStatus ().toString ())
                .data (statusTarefaAtualizada.getDataFim ().format (DateTimeFormatter.ofPattern ("dd/MM/yyyy")))
                .build ();

        return new ResponseEntity<> (atualizarStatusTarefaResponse, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tarefa previamente registrada no banco de dados")
    public ResponseEntity<AtualizarTarefaResponse> update(@PathVariable Long id,
                                                          @RequestBody AtualizarTarefaRequest request) {

        Tarefa tarefaAtualizada = tarefaService.update (id, request);

        AtualizarTarefaResponse atualizarTarefaResponse = AtualizarTarefaResponse
                .builder ()
                .id (tarefaAtualizada.getId ())
                .titulo (tarefaAtualizada.getTitulo ())
                .statusTarefa (tarefaAtualizada.getStatus ().toString ())
                .descricao (tarefaAtualizada.getDescricao ())
                .build ();


        return new ResponseEntity<> (atualizarTarefaResponse, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma tarefa previamente cadastrada pelo id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.tarefaService.delete(id);
        return ResponseEntity.noContent().build();
    }




}
