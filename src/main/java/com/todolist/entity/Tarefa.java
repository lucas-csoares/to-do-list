package com.todolist.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.todolist.enums.PrioridadeTarefa;
import com.todolist.enums.StatusTarefa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_tarefas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Todos os detalhes da tarefa")
public class Tarefa implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status da tarefa é obrigatório")
    private StatusTarefa status;

    @Column(name = "data_inicio")
    @JsonIgnore
    @CreationTimestamp
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    @JsonIgnore
    private LocalDate dataFim;

    @Column(name = "data_previsao")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPrevisao;

    @Column(name = "prazo_em_dias")
    @Min (value = 1, message = "A tarefa tem que ter no mínimo 1 dia de prazo")
    @Max (value = 365, message = "A tarefa não pode ultrapassar 365 dias de prazo")
    private Long prazo;

    @Enumerated(EnumType.STRING)
    private PrioridadeTarefa prioridade;

    @Column(name = "data_atualizacao")
    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

}

