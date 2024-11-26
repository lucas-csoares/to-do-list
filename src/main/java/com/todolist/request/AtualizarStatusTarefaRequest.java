package com.todolist.request;

import com.todolist.enums.StatusTarefa;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Possui atributos de requisição para atualizar status de uma tarefa")
public class AtualizarStatusTarefaRequest {
    private StatusTarefa status;
}
