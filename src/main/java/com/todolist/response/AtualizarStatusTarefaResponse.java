package com.todolist.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@Schema(description = "Todos os detalhes da resposta de uma atualização de status da tarefa")
public class AtualizarStatusTarefaResponse {
    private String status;
    private String dataInicio;
    private String dataFim;
}

