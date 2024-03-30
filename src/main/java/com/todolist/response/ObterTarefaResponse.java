package com.todolist.response;

import com.todolist.enums.StatusTarefa;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ObterTarefaResponse {

    private Long id;

    private String titulo;

    private String descricao;

    private StatusTarefa status;

    private String dataInicio;

    private String dataFim;
}
