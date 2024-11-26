package com.todolist.controller;

import com.todolist.entity.Tarefa;
import com.todolist.request.AtualizarTarefaRequest;
import com.todolist.request.CreateTarefaRequest;
import com.todolist.response.*;
import com.todolist.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.todolist.utils.ConverterResponseUtil.*;

@RestController
@RequestMapping("/tarefa")
@Schema(description = "Atende a chamadas da API realizando operações CRUD")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;


    @PostMapping
    @Operation(summary = "Cadastra uma tarefa na base de dados")
    @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CriarTarefaResponse.class))})

    public ResponseEntity<CriarTarefaResponse> create(@Valid  @RequestBody CreateTarefaRequest request) {
        Tarefa tarefaSave = tarefaService.create (request);

        return new ResponseEntity<> (createTarefaResponse (tarefaSave), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Se não for enviado nenhum parâmetro via url, lista todas as tarefas cadastradas. Caso " +
            "contrário, retorna a tarefa que corresponde ao título.")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas recuperada com sucesso",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ObterTarefasPaginadasResponse.class))})

    public ResponseEntity<ObterTarefasPaginadasResponse> findByTarefa(
            @Valid @RequestParam(required = false) String titulo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        Page<Tarefa> tarefasPaginadas = titulo == null ?
                this.tarefaService.findAll (PageRequest.of (page, size)) :
                this.tarefaService.findByTitulo (titulo, PageRequest.of (page, size));


        return new ResponseEntity<> (obterTarefasPaginadasResponse (tarefasPaginadas),
                HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tarefa previamente registrada no banco de dados")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AtualizarTarefaResponse.class))})

    public ResponseEntity<AtualizarTarefaResponse> update(@PathVariable Long id,
                                                          @Valid @RequestBody AtualizarTarefaRequest request) {

        Tarefa tarefaAtualizada = tarefaService.update (id, request);
        return new ResponseEntity<> (atualizarTarefaResponse (tarefaAtualizada), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma tarefa previamente cadastrada pelo id")
    @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    @ApiResponse(responseCode = "422", description = "Tarefa não pode ser deletada")

    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.tarefaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualiza o status de uma tarefa para finalizada")
    @ApiResponse(responseCode = "200", description = "Status da tarefa atualizado com sucesso",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AtualizarStatusTarefaResponse.class))})

    public ResponseEntity<AtualizarStatusTarefaResponse> updateStatus(@PathVariable Long id) {

        Tarefa statusTarefaAtualizada = tarefaService.updateStatus (id);

        return new ResponseEntity<> (atualizarStatusTarefaResponse (statusTarefaAtualizada),
                HttpStatus.OK);
    }



}
