package com.todolist.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exceção personalizada de erros de atualização")
public class NaoPermitirAtualizarException extends RuntimeException {

    public NaoPermitirAtualizarException (String message) {
        super(message);
    }
}
