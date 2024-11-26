package com.todolist.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Schema(description = "Todos os detalhes da resposta de busca por uma tarefa ou lista de tarefas")
public class ObterTarefasPaginadasResponse {
    private Integer paginaAtual;
    private Long totalItens;
    private Integer totalPaginas;
    private List<ObterTarefaResponse> tarefas;
}
