package com.todolist.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Schema(description = "Todos os detalhes de resposta da atualização de uma tarefa")
public class AtualizarTarefaResponse {


    private Long id;
    private String statusTarefa;
    private String titulo;
    private String dataAtualizacao;
    private String dataPrevisao;
    private String prioridade;
    private Long prazo;
}
