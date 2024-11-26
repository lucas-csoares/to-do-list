package com.todolist.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exceção personalizada para a prioridade de uma tarefa")
public class PrioridadeException extends RuntimeException{

    public PrioridadeException(String message) {
        super(message);
    }

}
