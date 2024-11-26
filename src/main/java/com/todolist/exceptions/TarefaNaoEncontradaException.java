package com.todolist.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exceção personalizada para o caso de não encontrar uma tarefa buscada na base de dados")
public class TarefaNaoEncontradaException extends RuntimeException{
    public TarefaNaoEncontradaException() {
        super();
    }
}
