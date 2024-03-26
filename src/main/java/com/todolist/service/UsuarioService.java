package com.todolist.service;

import com.todolist.entity.Usuario;
import com.todolist.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Método para persistir um objeto do tipo Usuario no banco de dados
     * @param usuario objeto do tipo usuário
     * @return novo usuário criado
     */
    public Usuario create(Usuario usuario) {
        usuario.setSenha (passwordEncoder.encode (usuario.getSenha ()));

        return this.iUsuarioRepository.save (usuario);
    }

    /**
     * Se o usuário não tiver cadastrado o hibernate vai cadastrar e se tiver cadastrado ele vai atualizar
     * @param usuario objeto do tipo usuário
     * @return usuário atualizado
     */
    public Usuario update(Usuario usuario) {
        usuario.setSenha (passwordEncoder.encode (usuario.getSenha ()));

        return this.iUsuarioRepository.save (usuario);
    }

    /**
     * Deleta um usuário cadastrado na base de dados
     * @param usuario objeto do tipo usuário
     */
    public void delete(Long id) {
        this.iUsuarioRepository.deleteById (id);
    }

    /**
     * Recupera todos os usuários cadastrados na base de dados
     * @return lista do tipo Usuario
     */
    public List<Usuario> retrieveAll() {
        return this.iUsuarioRepository.findAll ();
    }

}
