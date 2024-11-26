package com.todolist.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Schema(description = "Todos os detalhes de resposta da criação de uma tarefa")
public class CriarTarefaResponse {
    private Long id;
    private String titulo;
    private String status;
    private String dataInicio;
    private String dataPrevisao;
    private String prioridade;
    private String prazo;
}
