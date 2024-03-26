package com.todolist.entity.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAutenticado {
    private String email;
    private String senha;
}
