package com.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todolist.enums.StatusTarefa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_tarefas")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Todos os detalhes da tarefa")
public class Tarefa implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(length = 50)
    private String descricao;

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

}

