package com.todolist.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Valores enumeration para prioridade da tarefa")
public enum PrioridadeTarefa {
    ALTA,
    MEDIA,
    BAIXA
}
