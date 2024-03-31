package com.todolist.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AtualizarStatusTarefaResponse {
    private String status;
    private String dataInicio;
    private String dataFim;
}
