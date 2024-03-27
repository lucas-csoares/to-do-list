package com.todolist.request;

import com.todolist.enums.StatusTarefa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTarefaRequest {
    private String titulo;
    private String descricao;
    private StatusTarefa statusTarefa;
}
