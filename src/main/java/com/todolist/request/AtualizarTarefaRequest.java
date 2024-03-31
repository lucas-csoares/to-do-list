package com.todolist.request;

import com.todolist.enums.StatusTarefa;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
public class AtualizarTarefaRequest {

    @NotBlank(message = "{cadastrar.tarefa.request.titulo.obrigatorio}")
    private String titulo;
    @Length(message = "{cadastrar.tarefa.request.descricao.limite}")
    private String descricao;

}
