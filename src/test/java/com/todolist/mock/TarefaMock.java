package com.todolist.mock;

import com.todolist.entity.Tarefa;
import com.todolist.enums.PrioridadeTarefa;
import io.swagger.v3.oas.annotations.Operation;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class TarefaMock {


    @Operation(description = "Cria um objeto mock com data de previsão")
    public static Tarefa obterTarefaMockComDataPrevisao() {
        return Tarefa.builder()
                .id(10L)
                .titulo("Tarefa com data de previsão")
                .prioridade(PrioridadeTarefa.ALTA)
                .dataPrevisao (LocalDate.of(2024, 6, 25))
                .build();
    }

    @Operation(description = "Cria um objeto mock com prazo")
    public static Tarefa obterTarefaMockComPrazo() {
        return Tarefa.builder()
                .id(11L)
                .titulo("Tarefa com prazo")
                .prioridade(PrioridadeTarefa.BAIXA)
                .prazo (10L)
                .build();
    }

    @Operation(description = "Cria um objeto mock livre")
    public static Tarefa obterTarefaMockLivre() {
        return Tarefa.builder()
                .id(12L)
                .titulo("Tarefa livre")
                .prioridade(PrioridadeTarefa.BAIXA)
                .build();
    }


    @Operation(description = "Cria um objeto mock com prazo e data de previsão")
    public static Tarefa obterTarefaMockComConflito() {
        return Tarefa.builder()
                .id(13L)
                .titulo("Tarefa com data de previsão e prazo")
                .prioridade(PrioridadeTarefa.BAIXA)
                .prazo (10L)
                .dataPrevisao (LocalDate.of(2024, 6, 25))
                .build();
    }


    @Operation(description = "Cria um objeto mock com data inferior a data atual")
    public static Tarefa obterTarefaMockComDataInvalida() {
        return Tarefa.builder()
                .id(14L)
                .titulo("Tarefa com data de previsão e prazo")
                .prioridade(PrioridadeTarefa.MEDIA)
                .dataPrevisao (LocalDate.of(2024, 5, 22))
                .build();
    }



}
