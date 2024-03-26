package com.todolist.controller;

import com.todolist.entity.Usuario;
import com.todolist.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Método que chama método de criar usuário do service
     * @param usuario objeto do tipo usuário vindo do corpo da requisição cujas validações devem ser consideradas
     * @return Caso o usuário seja salvo, status 201 (created)
     */
    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioSave = usuarioService.create (usuario);

        return new ResponseEntity<> ("Novo usuário criado de e-mail " + usuarioSave.getEmail (), HttpStatus.CREATED);
    }


    /**
     * Método que chama o método de atualizar um usuário cadastrado na base de dados
     * @param id um objeto do tipo Long vindo da variável path que representa o id do usuário
     * @param usuario um objeto do tipo usuário vindo do corpo de requisição
     * @return caso o usuário seja atualizado, o status é 200 (Ok)
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody @NotNull Usuario usuario) {

        usuario.setId (id);
        Usuario usuarioUpdate = usuarioService.create (usuario);

        return new ResponseEntity<> ("Novo usuário criado de e-mail " + usuarioUpdate.getEmail (), HttpStatus.OK);
    }


    /**
     * Método que chama o método de listar todos os usuários previamente cadastrados na base de dados
     * @return caso recupere todos os usuários do banco de dados, o status é 200 (ok)
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> retrieveAll() {
        return new ResponseEntity<> (usuarioService.retrieveAll (), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuarioService.delete (id);
    }


}
