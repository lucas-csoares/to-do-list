package com.todolist.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AtualizarTarefaResponse {


    private Long id;
    private String statusTarefa;
    private String titulo;
    private String descricao;
}
