package com.todolist.service;


import com.todolist.entity.Tarefa;

import com.todolist.enums.StatusTarefa;
import com.todolist.exceptions.*;
import com.todolist.repository.TarefaRepository;
import com.todolist.request.AtualizarTarefaRequest;
import com.todolist.request.CreateTarefaRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import static com.todolist.enums.StatusTarefa.EM_PROGRESSO;
import static com.todolist.enums.StatusTarefa.FINALIZADA;
import static java.time.LocalDate.now;


@Service
@Transactional
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;


    @Operation(summary = "Criar uma tarefa", description = "retorna a tarefa cadastrada com base nos dados")
    public Tarefa create(CreateTarefaRequest request) {

        Tarefa tarefaValidacao = this.tarefaRepository.findByTitulo (request.getTitulo ());


        verificarDataPrevisaoEPrazo (request.getDataPrevisao (), request.getPrazo ());


        if(tarefaValidacao != null)
            throw new TarefaExistenteException ("Já existe uma tarefa com mesmo título ou descrição");


        Tarefa tarefa = Tarefa.builder ()
                .status (EM_PROGRESSO)
                .titulo (request.getTitulo ())
                .dataPrevisao (request.getDataPrevisao ())
                .prazo (request.getPrazo ())
                .dataFim (null)
                .build ();

        return this.tarefaRepository.save (tarefa);
    }


    @Operation(summary = "Verifica validade de data da previsão e prazo", description = "lança uma exceção se data de" +
            " previsão e prazo não for nulo e se a data de previsão for anterior a data atual")
    public void verificarDataPrevisaoEPrazo(LocalDate dataPrevisao, Integer prazo) {
        if (dataPrevisao != null) {
            if (prazo != null) {
                throw new TarefaComPrazoEDataException();
            }

            if (now().isAfter(dataPrevisao)) {
                throw new DataException("A data de previsão é anterior à data atual");
            }
        }
    }


    @Operation(summary = "Pesquisa uma tarefa por título", description = "retorna a tarefa de acordo com o título " +
            "passado como argumento")
    public Page<Tarefa> findByTitulo(String titulo, Pageable pageable) {
        return this.tarefaRepository.findByTituloContainingOrderByDataAtualizacaoDesc (titulo, pageable);
    }

    public Page<Tarefa> findAll(Pageable pageable) {

        return this.tarefaRepository.findAllByOrderByDataAtualizacaoDesc (pageable);
    }



    public Tarefa update(Long id, AtualizarTarefaRequest request) {

        verificarDataPrevisaoEPrazo (request.getDataPrevisao (), request.getPrazo ());

        Tarefa tarefa = this.tarefaRepository.findById (id).get ();


        if(tarefa.getStatus ().equals (FINALIZADA))
            throw new NaoPermitirAtualizarException("Não é permitido atualizar uma tarefa finalizada");

        tarefa.setTitulo (request.getTitulo ());
        tarefa.setPrazo (request.getPrazo ());
        tarefa.setDataPrevisao (request.getDataPrevisao ());


        this.tarefaRepository.save (tarefa);
        return tarefa;
    }



    public Tarefa updateStatus (Long id) {
        Tarefa tarefa = this.tarefaRepository.findById (id).get ();

        if(tarefa.getStatus ().equals (EM_PROGRESSO)) {
            tarefa.setStatus (FINALIZADA);
            tarefa.setDataFim (now ());
        } else {
            tarefa.setStatus (EM_PROGRESSO);
            tarefa.setDataFim (null);
        }

        this.tarefaRepository.save (tarefa);
        return tarefa;
    }


    public void delete(Long id) {
        Tarefa tarefa = this.tarefaRepository.findById (id).get ();

        if(!EM_PROGRESSO.equals (tarefa.getStatus ()))
            throw new NaoPermitirExcluirException ();

        this.tarefaRepository.deleteById (id);
    }







}
