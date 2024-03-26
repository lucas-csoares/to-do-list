package com.todolist.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todolist.security.permissoes.PermissaoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Role implements Serializable {

    //private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private PermissaoEnum nome;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<Usuario> usuarios;

}
