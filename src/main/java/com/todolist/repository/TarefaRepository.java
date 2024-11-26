package com.todolist.repository;

import com.todolist.entity.Tarefa;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Schema(description = "Possui métodos de acesso ao banco de dados mediante JDBC")
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    Tarefa findByTitulo(String titulo);

    Page<Tarefa> findByTituloContainingOrderByDataAtualizacaoDesc(String titulo, Pageable pegeable);
    Page<Tarefa> findAllByOrderByDataAtualizacaoDesc(Pageable pegeable);


}
