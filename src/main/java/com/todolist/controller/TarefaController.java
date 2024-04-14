package com.todolist.controller;

import com.todolist.entity.Tarefa;
import com.todolist.enums.StatusTarefa;
import com.todolist.request.AtualizarStatusTarefaRequest;
import com.todolist.request.AtualizarTarefaRequest;
import com.todolist.request.CreateTarefaRequest;
import com.todolist.response.*;
import com.todolist.service.TarefaService;
import com.todolist.utils.DataUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    public ResponseEntity<CriarTarefaResponse> create(@Valid  @RequestBody CreateTarefaRequest request) {
        Tarefa tarefaSave = tarefaService.create (request);

        CriarTarefaResponse criarTarefaResponses = CriarTarefaResponse
                .builder ()
                .dataInicio (tarefaSave.getDataInicio ().format (DataUtil.DATA))
                .id (tarefaSave.getId ())
                .status ("Em progresso")
                .dataPrevisao (tarefaSave.getDataPrevisao () != null ?
                        tarefaSave.getDataPrevisao ().format (DataUtil.DATA) : null)
                .prazo (tarefaSave.getPrazo ())
                .titulo (tarefaSave.getTitulo ())
                .build ();

        return new ResponseEntity<> (criarTarefaResponses, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Se não for enviado nenhum parâmetro via url, lista todas as tarefas cadastradas. Caso " +
            "contrário, lista as tarefas cadastradas por título.")

    public ResponseEntity<ObterTarefasPaginadasResponse> findByTarefa(
            @Valid @RequestParam(required = false) String titulo,
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
                            .status (tarefa.getStatus ())
                            .dataPrevisao (tarefa.getDataPrevisao () != null ?
                                    tarefa.getDataPrevisao ().format (DataUtil.DATA) : null)
                            .prazo (tarefa.getPrazo ())
                            .dataInicio (tarefa.getDataInicio().format (DataUtil.DATA))
                            .dataFim (tarefa.getDataFim () != null ?
                                    tarefa.getDataFim ().format (DataUtil.DATA) : null)
                            .dataAtualizacao (tarefa.getDataAtualizacao ().format (DataUtil.DATA_HORA))
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


    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualiza o status de uma tarefa para finalizada")
    public ResponseEntity<AtualizarStatusTarefaResponse> updateStatus(@PathVariable Long id) {

        Tarefa statusTarefaAtualizada = tarefaService.updateStatus (id);

        AtualizarStatusTarefaResponse atualizarStatusTarefaResponse = AtualizarStatusTarefaResponse
                .builder ()
                .status (statusTarefaAtualizada.getStatus ().toString ())
                .dataInicio (statusTarefaAtualizada.getDataInicio ().format (DataUtil.DATA))
                .dataFim (statusTarefaAtualizada.getDataFim ().format (DataUtil.DATA))
                .build ();

        return new ResponseEntity<> (atualizarStatusTarefaResponse, HttpStatus.OK);
    }



    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tarefa previamente registrada no banco de dados")
    public ResponseEntity<AtualizarTarefaResponse> update(@PathVariable Long id,
                                                          @Valid @RequestBody AtualizarTarefaRequest request) {

        Tarefa tarefaAtualizada = tarefaService.update (id, request);

        AtualizarTarefaResponse atualizarTarefaResponse = AtualizarTarefaResponse
                .builder ()
                .id (tarefaAtualizada.getId ())
                .titulo (tarefaAtualizada.getTitulo ())
                .statusTarefa (tarefaAtualizada.getStatus ().toString ())
                .dataAtualizacao (tarefaAtualizada.getDataAtualizacao ().format (DataUtil.DATA_HORA))
                .dataPrevisao (tarefaAtualizada.getDataPrevisao () != null ?
                        tarefaAtualizada.getDataPrevisao ().format (DataUtil.DATA) : null)
                .prazo (tarefaAtualizada.getPrazo ())
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
