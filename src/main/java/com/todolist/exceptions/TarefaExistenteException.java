package com.todolist.exceptions;

public class TarefaExistenteException extends RuntimeException{

    public TarefaExistenteException (String message) {
        super(message);
    }
}
