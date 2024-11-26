package com.todolist.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exceção personalizada para tarefas com mais de um tipo (data, prazo, livre")
public class TarefaComPrazoEDataException extends RuntimeException{

    public TarefaComPrazoEDataException() {
        super();
    }

}
