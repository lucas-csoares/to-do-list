package com.todolist.response;

import com.todolist.enums.StatusTarefa;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Schema(description = "Todos os detalhes da resposta de busca de uma tarefa")
public class ObterTarefaResponse {

    private Long id;

    private String titulo;

    private StatusTarefa status;

    private String dataInicio;

    private String dataFim;

    private String dataAtualizacao;

    private String dataPrevisao;

    private String prioridade;

    private String prazo;

    private String statusConformeTipo;
}
