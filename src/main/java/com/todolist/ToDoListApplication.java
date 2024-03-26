package com.todolist;

import com.todolist.entity.Role;
import com.todolist.entity.Usuario;
import com.todolist.security.permissoes.PermissaoEnum;
import com.todolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ToDoListApplication implements CommandLineRunner {

	@Autowired
	private UsuarioService usuarioService;

	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Usuario usuario = new Usuario();
		usuario.setFullName ("Lucas Soares");
		usuario.setEmail ("polonio230@hotmail.com");
		usuario.setSenha ("12345678");


		List<Role> roles = new ArrayList<> ();

		Role roleAdmin = new Role();
		roleAdmin.setNome(PermissaoEnum.ADMIN);

		Role roleUsuario = new Role();
		roleUsuario.setNome(PermissaoEnum.USUARIO);

		roles.add(roleAdmin);
		roles.add(roleUsuario);

		usuario.setRoles(roles);

		usuarioService.create (usuario);
	}
}
