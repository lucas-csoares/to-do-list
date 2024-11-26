package com.todolist.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exceção personalizada de datas")
public class DataException extends RuntimeException{

    public DataException(String message) {
        super(message);
    }
}
