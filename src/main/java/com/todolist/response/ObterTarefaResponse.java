package com.todolist.response;

import com.todolist.enums.StatusTarefa;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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

    private String dataAtualizacao;

    private String dataPrevisao;

    private Integer prazo;
}
