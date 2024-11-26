package com.todolist.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exceção personalizada para tentativa de criar tarefas que já existem")
public class TarefaExistenteException extends RuntimeException{

    public TarefaExistenteException (String message) {
        super(message);
    }
}
