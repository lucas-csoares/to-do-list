package com.todolist.service;


import com.todolist.entity.Tarefa;
import com.todolist.enums.StatusTarefa;
import com.todolist.exceptions.NaoPermitirAtualizarException;
import com.todolist.exceptions.NaoPermitirExcluirException;
import com.todolist.exceptions.TarefaExistenteException;
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

        Tarefa tarefaValidacao = this.tarefaRepository.findByTituloOrDescricao (request.getTitulo (),
                request.getDescricao ());

        if(tarefaValidacao != null)
            throw new TarefaExistenteException ("Já existe uma tarefa com mesmo título ou descrição");


        Tarefa tarefa = Tarefa.builder ()
                .status (StatusTarefa.EM_PROGRESSO)
                .titulo (request.getTitulo ())
                .descricao (request.getDescricao ())
                .dataFim (null)
                .build ();

        return this.tarefaRepository.save (tarefa);
    }


    public Page<Tarefa> findByTitulo(String titulo, Pageable pageable) {
        return this.tarefaRepository.findByTituloContainingOrderByDataAtualizacaoDesc (titulo, pageable);
    }

    public Page<Tarefa> findAll(Pageable pageable) {

        return this.tarefaRepository.findAllByOrderByDataAtualizacaoDesc (pageable);
    }

    public Tarefa update(Long id, AtualizarTarefaRequest request) {

        Tarefa tarefa = this.tarefaRepository.findById (id).get ();

        if(tarefa.getStatus ().equals (StatusTarefa.FINALIZADA))
            throw new NaoPermitirAtualizarException("Não é permitido atualizar uma tarefa finalizada");

        tarefa.setTitulo (request.getTitulo ());
        tarefa.setDescricao (request.getDescricao ());

        this.tarefaRepository.save (tarefa);
        return tarefa;
    }

    public Tarefa updateStatus (Long id) {
        Tarefa tarefa = this.tarefaRepository.findById (id).get ();

        if(tarefa.getStatus ().equals (StatusTarefa.EM_PROGRESSO)) {
            tarefa.setStatus (StatusTarefa.FINALIZADA);
            tarefa.setDataFim (LocalDate.now ());
        } else {
            tarefa.setStatus (StatusTarefa.EM_PROGRESSO);
            tarefa.setDataFim (null);
        }

        this.tarefaRepository.save (tarefa);
        return tarefa;
    }


    public void delete(Long id) {
        Tarefa tarefa = this.tarefaRepository.findById (id).get ();

        if(!StatusTarefa.EM_PROGRESSO.equals (tarefa.getStatus ()))
            throw new NaoPermitirExcluirException ();

        this.tarefaRepository.deleteById (id);
    }







}
