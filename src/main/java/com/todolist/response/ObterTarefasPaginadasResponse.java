package com.todolist.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ObterTarefasPaginadasResponse {
    private Integer paginaAtual;
    private Long totalItens;
    private Integer totalPaginas;
    private List<ObterTarefaResponse> tarefas;
}
