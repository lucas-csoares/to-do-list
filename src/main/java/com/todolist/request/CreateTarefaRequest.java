package com.todolist.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateTarefaRequest {

    @NotBlank(message = "{cadastrar.tarefa.request.titulo.obrigatorio}")
    private String titulo;
    @Length(max = 50, message = "{cadastrar.tarefa.request.descricao.limite}")
    private String descricao;
}
