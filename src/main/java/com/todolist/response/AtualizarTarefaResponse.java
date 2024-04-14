package com.todolist.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AtualizarTarefaResponse {


    private Long id;
    private String statusTarefa;
    private String titulo;
    private String descricao;
    private String dataAtualizacao;

    private String dataPrevisao;
    private Integer prazo;
}
