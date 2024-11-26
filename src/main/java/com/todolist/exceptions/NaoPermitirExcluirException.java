package com.todolist.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exceção personalizada para erros de exclusão da tarefa")
public class NaoPermitirExcluirException extends RuntimeException{

    public NaoPermitirExcluirException () {
        super();
    }
}
