package com.todolist.exceptions;

import com.todolist.exceptions.enums.ErrosEnum;
import com.todolist.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@Schema(description = "Acessada ao ser lançado uma exceção")
public class GlobalExceptionHandler {

    @Operation(summary = "Captura a exceção de não permitir exclusão da tarefa e a trata")
    @ExceptionHandler(NaoPermitirExcluirException.class)
    public ResponseEntity<ErrorResponse> naoPermitirExcluirExceptionHandler() {

        Map<String, String> response = new HashMap<> ();
        response.put ("codigo", ErrosEnum.NAO_PERMITIR_EXCLUIR.toString ());
        response.put ("mensagem", "Não é permitido excluir uma tarefa com status diferente de PROGRESSO");

        ErrorResponse errorResponse = ErrorResponse
                .builder ()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.toString ())
                .erros(Collections.singletonList (response))
                .build ();


        return new ResponseEntity<> (errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @Operation(summary = "Captura uma exceção de não permissão para atualizar e a trata")
    @ExceptionHandler(NaoPermitirAtualizarException.class)
    public ResponseEntity<ErrorResponse> naoPermitirAtualizarException(NaoPermitirAtualizarException erro) {

        Map<String, String> response = new HashMap<> ();
        response.put ("codigo", ErrosEnum.NAO_PERMITIR_ATUALIZAR.toString ());
        response.put ("mensagem", erro.getMessage ());

        ErrorResponse errorResponse = ErrorResponse
                .builder ()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.toString ())
                .erros(Collections.singletonList (response))
                .build ();


        return new ResponseEntity<> (errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @Operation(summary = "Captura uma exceção de existência da tarefa e a trata")
    @ExceptionHandler(TarefaExistenteException.class)
    public ResponseEntity<ErrorResponse> tarefaExistenteExceptionHandler(TarefaExistenteException erro) {

        Map<String, String> response = new HashMap<> ();
        response.put ("codigo", ErrosEnum.TAREFA_JA_EXISTENTE.toString ());
        response.put ("mensagem", erro.getMessage ());

        ErrorResponse errorResponse = ErrorResponse
                .builder ()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.toString ())
                .erros(Collections.singletonList (response))
                .build ();


        return new ResponseEntity<> (errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @Operation(summary = "Captura uma exceção de tarefa com prazo e data e a trata")
    @ExceptionHandler(TarefaComPrazoEDataException.class)
    public ResponseEntity<ErrorResponse> TarefaComPrazoEDataExceptionHandler() {

        Map<String, String> response = new HashMap<> ();
        response.put ("codigo", ErrosEnum.NAO_PERMITIR_CRIAR.toString ());
        response.put ("mensagem", "Só é permitido criar tarefa de um tipo: ou data ou prazo ou livre");

        ErrorResponse errorResponse = ErrorResponse
                .builder ()
                .status(HttpStatus.BAD_REQUEST.toString ())
                .erros(Collections.singletonList (response))
                .build ();


        return new ResponseEntity<> (errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Captura uma exceção de prioridade da tarefa e a trata")
    @ExceptionHandler(PrioridadeException.class)
    public ResponseEntity<ErrorResponse> priodidadeExceptionHandler(PrioridadeException erro) {

        Map<String, String> response = new HashMap<> ();
        response.put ("codigo", ErrosEnum.TAREFA_SEM_PRIORIDADE.toString ());
        response.put ("mensagem", erro.getMessage ());

        ErrorResponse errorResponse = ErrorResponse
                .builder ()
                .status(HttpStatus.BAD_REQUEST.toString ())
                .erros(Collections.singletonList (response))
                .build ();


        return new ResponseEntity<> (errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Captura uma exceção da data e a trata")
    @ExceptionHandler(DataException.class)
    public ResponseEntity<ErrorResponse> DataExceptionHandler(DataException erro) {

        Map<String, String> response = new HashMap<> ();
        response.put ("codigo", ErrosEnum.DATA_INVALIDA.toString ());
        response.put ("mensagem", erro.getMessage ());

        ErrorResponse errorResponse = ErrorResponse
                .builder ()
                .status(HttpStatus.BAD_REQUEST.toString ())
                .erros(Collections.singletonList (response))
                .build ();


        return new ResponseEntity<> (errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Captura uma exceção de tarefa não encontrada e a trata")
    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> tarefaNaoEncontradaExceptionHandler() {

        Map<String, String> response = new HashMap<> ();
        response.put ("codigo", ErrosEnum.TAREFA_NAO_ENCONTRADA.toString ());
        response.put ("mensagem", "A tarefa não existe no banco de dados");

        ErrorResponse errorResponse = ErrorResponse
                .builder ()
                .status(HttpStatus.NOT_FOUND.toString ())
                .erros(Collections.singletonList (response))
                .build ();


        return new ResponseEntity<> (errorResponse, HttpStatus.NOT_FOUND);
    }





}

