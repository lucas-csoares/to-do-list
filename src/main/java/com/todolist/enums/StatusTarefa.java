package com.todolist.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Valores enumeration que indica se a tarefa acabou ou está em curso")
public enum StatusTarefa {
    EM_PROGRESSO,
    FINALIZADA
}
