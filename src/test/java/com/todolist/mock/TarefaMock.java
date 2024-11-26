package com.todolist.mock;

import com.todolist.entity.Tarefa;
import com.todolist.enums.PrioridadeTarefa;
import com.todolist.enums.StatusTarefa;
import com.todolist.request.AtualizarTarefaRequest;
import com.todolist.request.CreateTarefaRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.experimental.UtilityClass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class TarefaMock {


    @Operation(description = "Cria um objeto mock com data de previsão")
    public static Tarefa obterTarefaMockComDataPrevisao() {
        return Tarefa.builder()
                .id(10L)
                .titulo("Tarefa com data de previsão")
                .status(StatusTarefa.EM_PROGRESSO)
                .dataInicio(LocalDate.now())
                .dataAtualizacao(LocalDateTime.now())
                .dataPrevisao(LocalDate.of(2024, 9, 25))
                .prioridade(PrioridadeTarefa.ALTA)
                .prazo(null)
                .build();
    }


    @Operation(description = "Cria um objeto mock com status finalizada")
    public static Tarefa obterTarefaMockFinalizada() {
        return Tarefa.builder()
                .id(15L)
                .titulo("Tarefa finalizada")
                .status(StatusTarefa.FINALIZADA)
                .dataInicio(LocalDate.now())
                .dataAtualizacao(LocalDateTime.now())
                .dataPrevisao(LocalDate.of(2024, 6, 25))
                .prioridade(PrioridadeTarefa.ALTA)
                .prazo(null)
                .build();
    }

    @Operation(description = "Cria um objeto mock sem ID")
    public static Tarefa obterTarefaMockSemId() {
        return Tarefa.builder()
                .titulo("Tarefa sem ID")
                .status(StatusTarefa.FINALIZADA)
                .dataInicio(LocalDate.now())
                .dataAtualizacao(LocalDateTime.now())
                .dataPrevisao(LocalDate.of(2024, 6, 25))
                .prioridade(PrioridadeTarefa.ALTA)
                .prazo(null)
                .build();
    }

    @Operation(description = "Cria um objeto mock com prazo")
    public static Tarefa obterTarefaMockComPrazo() {
        return Tarefa.builder()
                .id(11L)
                .titulo("Tarefa com prazo")
                .status(StatusTarefa.FINALIZADA)
                .dataInicio(LocalDate.now())
                .dataAtualizacao(LocalDateTime.now())
                .dataPrevisao(null)
                .prioridade(PrioridadeTarefa.MEDIA)
                .prazo(10L)
                .build();
    }

    @Operation(description = "Cria um objeto mock Livre (sem prazo e nem data de previsão)")
    public static Tarefa obterTarefaMockLivre() {
        return Tarefa.builder()
                .id(12L)
                .titulo("Tarefa livre")
                .status(StatusTarefa.EM_PROGRESSO)
                .dataInicio(LocalDate.now())
                .dataAtualizacao(LocalDateTime.now())
                .dataPrevisao(null)
                .prioridade(PrioridadeTarefa.BAIXA)
                .prazo(null)
                .build();
    }


    public static CreateTarefaRequest tarefaComDataPrevisao() {
        return CreateTarefaRequest.builder()
                .titulo("Tarefa teste (version 17)")
                .dataPrevisao(LocalDate.now ().plusYears (1L))
                .prioridade(PrioridadeTarefa.ALTA)
                .build();
    }


    public static CreateTarefaRequest tarefaSemPrioridade() {
        return CreateTarefaRequest.builder()
                .titulo("Tarefa com data de previsão")
                .prazo(10L)
                .build();
    }


    public static CreateTarefaRequest tarefaConflitante() {
        return CreateTarefaRequest.builder()
                .titulo("Tarefa conflitante")
                .dataPrevisao(LocalDate.of(2024, 6, 20))
                .prazo(10L)
                .prioridade(PrioridadeTarefa.ALTA)
                .build();
    }

    public static CreateTarefaRequest tarefaComDataPrevisaoInferior() {
        return CreateTarefaRequest.builder()
                .titulo("Tarefa com data de previsão inferior")
                .dataPrevisao(LocalDate.now().minusDays(1))
                .prioridade(PrioridadeTarefa.BAIXA)
                .build();
    }

    @Operation(description = "Cria um objeto mock para atualização de tarefa com data de previsão")
    public static AtualizarTarefaRequest atualizarTarefaComDataPrevisao() {
        return AtualizarTarefaRequest.builder()
                .titulo("Tarefa com data de previsão atualizada (version 2)")
                .dataPrevisao(LocalDate.now ().plusYears (1L))
                .prioridade(PrioridadeTarefa.MEDIA)
                .build();
    }


}
