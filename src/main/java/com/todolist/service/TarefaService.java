package com.todolist.service;


import com.todolist.entity.Tarefa;
import com.todolist.enums.StatusTarefa;
import com.todolist.repository.TarefaRepository;
import com.todolist.request.AtualizarStatusTarefaRequest;
import com.todolist.request.AtualizarTarefaRequest;
import com.todolist.request.CreateTarefaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;


    public Tarefa create(CreateTarefaRequest request) {
        Tarefa tarefa = Tarefa.builder ()
                .status (StatusTarefa.EM_PROGRESSO)
                .titulo (request.getTitulo ())
                .descricao (request.getDescricao ())
                .dataFim (null)
                .build ();

        return this.tarefaRepository.save (tarefa);
    }


    public Page<Tarefa> findByTitulo(String titulo, Pageable pageable) {
        return this.tarefaRepository.findByTituloContaining (titulo, pageable);
    }

    public Page<Tarefa> findAll(Pageable pageable) {
        return this.tarefaRepository.findAll (pageable);
    }

    public Tarefa update(Long id, AtualizarTarefaRequest request) {

        Tarefa tarefa = this.tarefaRepository.findById (id).get ();

        tarefa.setTitulo (request.getTitulo ());
        tarefa.setDescricao (request.getDescricao ());

        this.tarefaRepository.save (tarefa);
        return tarefa;
    }

    public Tarefa updateStatus (Long id, AtualizarStatusTarefaRequest request) {
        Tarefa tarefa = this.tarefaRepository.findById (id).get ();

        tarefa.setStatus (request.getStatus ());
        tarefa.setDataFim (tarefa.getStatus () == StatusTarefa.FINALIZADA ? LocalDate.now () : null);

        this.tarefaRepository.save (tarefa);
        return tarefa;
    }


    public void delete(Long id) {
        this.tarefaRepository.deleteById (id);
    }







}
