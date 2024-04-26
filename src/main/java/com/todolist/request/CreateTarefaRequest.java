package com.todolist.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.todolist.enums.PrioridadeTarefa;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
public class CreateTarefaRequest {

    @NotBlank(message = "{cadastrar.tarefa.request.titulo.obrigatorio}")
    private String titulo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate dataPrevisao;

    private Long prazo;

    @Enumerated(EnumType.STRING)
    private PrioridadeTarefa prioridade;
}
