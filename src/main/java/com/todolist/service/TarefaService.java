package com.todolist.service;

import com.todolist.entity.Tarefa;
import com.todolist.exceptions.*;
import com.todolist.repository.TarefaRepository;
import com.todolist.request.AtualizarTarefaRequest;
import com.todolist.request.CreateTarefaRequest;
import com.todolist.service.interfaces.OperacoesCRUDService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.todolist.enums.StatusTarefa.EM_PROGRESSO;
import static com.todolist.enums.StatusTarefa.FINALIZADA;
import static java.time.LocalDate.now;


@Service
@Transactional
@Schema(description = "Realiza operações de CRUD e salva objeto na base de dados")
public class TarefaService implements OperacoesCRUDService<Tarefa, CreateTarefaRequest, AtualizarTarefaRequest> {

    @Autowired
    private TarefaRepository tarefaRepository;


    @Operation(summary = "Pesquisa uma tarefa por título", description = "retorna a tarefa de acordo com o título " +
            "passado como argumento")
    public Page<Tarefa> findByTitulo(@Parameter(description = "Título da tarefa a ser recuperada", required = true) String titulo, @Parameter(description = "Paginação com a página e o tamanho da página", required = true) Pageable pageable) {
        return this.tarefaRepository.findByTituloContainingOrderByDataAtualizacaoDesc (titulo, pageable);
    }

    @Operation(summary = "Lista todas as tarefas", description = "retorna a lista de tarefas na ordem da mais " +
            "recentemente atualizada")
    public Page<Tarefa> findAll(@Parameter(description = "Paginação correspondente a lista de tarefas retornada", required = true) Pageable pageable) {

        return this.tarefaRepository.findAllByOrderByDataAtualizacaoDesc (pageable);
    }

    @Operation(summary = "Criar uma tarefa", description = "retorna a tarefa cadastrada na base de dados")
    @Override
    public Tarefa create(@Parameter(description = "Objeto de transferência que vai ser convertido em objeto tarefa", required = true) CreateTarefaRequest request) {

        if(request.getPrioridade () == null)
            throw new PrioridadeException ("A tarefa tem que ter prioridade!");

       checkIfTaskExists (request.getTitulo ());

       verificarDataPrevisaoEPrazo (request.getDataPrevisao (), request.getPrazo ());

        Tarefa tarefa = Tarefa.builder ()
                .status (EM_PROGRESSO)
                .titulo (request.getTitulo ())
                .dataPrevisao (request.getDataPrevisao ())
                .prazo (request.getPrazo ())
                .prioridade (request.getPrioridade ())
                .dataFim (null)
                .build ();

        return this.tarefaRepository.save (tarefa);
    }



    @Operation(summary = "Atualiza uma tarefa por id", description = "Retorna a tarefa atualizada")
    @Override
    public Tarefa update(@Parameter(description = "id da tarefa a ser atualizada na base de dados", required = true) Long id, @Parameter(description = "Objeto de transferência que vai ser convertido em objeto tarefa afim de salvar na base de dados", required = true) AtualizarTarefaRequest request) {


        verificarDataPrevisaoEPrazo (request.getDataPrevisao (), request.getPrazo ());

        Tarefa tarefa = this.tarefaRepository.findById(id).orElseThrow(TarefaNaoEncontradaException::new);

        if(!tarefa.getTitulo ().equals (request.getTitulo ()))
            checkIfTaskExists (request.getTitulo ());


        if(tarefa.getStatus ().equals (FINALIZADA))
            throw new NaoPermitirAtualizarException("Não é permitido atualizar uma tarefa finalizada");

        tarefa.setTitulo (request.getTitulo ());
        tarefa.setPrazo (request.getPrazo ());
        tarefa.setDataPrevisao (request.getDataPrevisao ());
        tarefa.setPrioridade (request.getPrioridade ());


        this.tarefaRepository.save (tarefa);
        return tarefa;
    }

    @Operation(summary = "Deleta uma tarefa pelo seu id", description = "recupera a tarefa e a deleta da base de " +
            "dados podendo lançar exceção em caso estar com status diferente de EM_PROGRESSO")
    @Override
    public void delete(@Parameter(description = "id da tarefa a ser deletada da base de dados", required = true) Long id) {

        Tarefa tarefa = this.tarefaRepository.findById(id).orElseThrow(TarefaNaoEncontradaException::new);


        if(!EM_PROGRESSO.equals (tarefa.getStatus ()))
            throw new NaoPermitirExcluirException ();

        this.tarefaRepository.deleteById (id);
    }


    @Operation(summary = "Verifica validade de data da previsão e prazo", description = "lança uma exceção se data de" +
            " previsão e prazo não for nulo e se a data de previsão for anterior a data atual")
    private void verificarDataPrevisaoEPrazo(@Parameter(description = "Data que vai ser verificada", required = true) LocalDate dataPrevisao, @Parameter(description = "Tipo Long que corresponde ao prazo da tarefa", required = true) Long prazo) {

        if (dataPrevisao != null) {

            if (prazo != null)
                throw new TarefaComPrazoEDataException();

            if (now().isAfter(dataPrevisao))
                throw new DataException("A data de previsão é anterior à data atual");

        }
    }



    @Operation(summary = "Verifica a existência da tarefa", description = "Caso a tarefa já exista na base de dados, " +
            "lança exceção")
    public void checkIfTaskExists(@Parameter(description = "Título que vai ser verificado se existe ou não",
            required = true) String titulo) {

        Tarefa tarefaValidacao = this.tarefaRepository.findByTitulo (titulo);

        if(tarefaValidacao != null)
            throw new TarefaExistenteException ("Já existe uma tarefa com mesmo título");
    }


    @Operation(summary = "Atualiza o status da tarefa", description = "retorna a tarefa cujo status foi atualizado")
    public Tarefa updateStatus (@Parameter(description = "id da tarefa cujo status será atualizado", required = true) Long id) {
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

}
