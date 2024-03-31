package com.todolist.exceptions;

public class NaoPermitirAtualizarException extends RuntimeException {

    public NaoPermitirAtualizarException (String message) {
        super(message);
    }
}
