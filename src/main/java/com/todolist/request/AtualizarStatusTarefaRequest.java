package com.todolist.request;

import com.todolist.enums.StatusTarefa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarStatusTarefaRequest {
    private StatusTarefa status;
}
