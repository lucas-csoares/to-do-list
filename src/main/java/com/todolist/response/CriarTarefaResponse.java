package com.todolist.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CriarTarefaResponse {
    private Long id;
    private String titulo;
    private String status;
    private String dataInicio;
    private String dataPrevisao;
    private String prioridade;
    private Integer prazo;
}
