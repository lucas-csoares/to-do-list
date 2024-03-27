package com.todolist.entity;

import com.todolist.enums.StatusTarefa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "TB_TAREFA")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tarefa implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(length = 1000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status da tarefa é obrigatório")
    private StatusTarefa status;

}

