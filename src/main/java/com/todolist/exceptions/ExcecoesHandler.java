package com.todolist.exceptions;

import com.todolist.exceptions.enums.ErrosEnum;
import com.todolist.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Outras métodos para lidar com exceções podem ser adicionados
 */
@ControllerAdvice
public class ExcecoesHandler {

    @ExceptionHandler(NaoPermitirExcluirException.class)
    public ResponseEntity<ErrorResponse> naoPermitirExcluirExceptionHandler(NaoPermitirExcluirException erro) {

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






}
