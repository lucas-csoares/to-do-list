package com.todolist.exceptions.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Valores enumeration para impressão de tipos de erros")
public enum ErrosEnum {
        NAO_PERMITIR_EXCLUIR, NAO_PERMITIR_ATUALIZAR, TAREFA_JA_EXISTENTE, NAO_PERMITIR_CRIAR, DATA_INVALIDA,
        TAREFA_NAO_ENCONTRADA, TAREFA_SEM_PRIORIDADE
}
