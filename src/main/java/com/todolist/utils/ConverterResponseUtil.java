package com.todolist.utils;

import com.todolist.entity.Tarefa;
import com.todolist.enums.StatusTarefa;
import com.todolist.response.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import java.time.temporal.ChronoUnit;
import java.util.List;
import static com.todolist.utils.DataUtil.*;
import static com.todolist.utils.DataUtil.formatarData;
import static java.time.LocalDate.now;

@UtilityClass
@Schema(description = "Classe utilitária que contém todos os métodos de conversão de objeto de transferência de dados" +
        " em objeto da entidade Tarefa")
public class ConverterResponseUtil {


    public static CriarTarefaResponse createTarefaResponse(Tarefa tarefa) {

        CriarTarefaResponse criarTarefaResponse = CriarTarefaResponse
                .builder ()
                .dataInicio (tarefa.getDataInicio ().format (DATA))
                .id (tarefa.getId ())
                .status ("Em progresso")
                .dataPrevisao (formatarData (tarefa.getDataPrevisao ()))
                .prioridade (tarefa.getPrioridade () != null ? tarefa.getPrioridade ().toString () : null)
                .prazo (tarefa.getPrazo () != null ?  tarefa.getPrazo () + " dias" : null)
                .titulo (tarefa.getTitulo ())
                .build ();

        return criarTarefaResponse;

    }

    //Data posterior: LocalDate.of(2024,5,15)

    private Long calcPrazo(Tarefa tarefa) {
        Long difDias;

        if(tarefa.getPrazo () != null) {
            difDias = ChronoUnit.DAYS.between (tarefa.getDataInicio (), now ());
            return tarefa.getDataInicio ().isAfter (now ()) ? difDias :
                    tarefa.getPrazo () - difDias;
        } else if (tarefa.getDataPrevisao () != null) {
            difDias = ChronoUnit.DAYS.between (now (), tarefa.getDataPrevisao ());
            return difDias;
        }
        return null;
    }

    private String formatDiasRestantes(Tarefa tarefa) {

        if (tarefa.getStatus () == StatusTarefa.FINALIZADA)
            return "Concluída";

        Long diasRestantes = calcPrazo (tarefa);

        if (diasRestantes != null)
            if (diasRestantes < 0)
                return "Você está a " + (diasRestantes * (-1)) + " dias atrasado.";

        return "Prevista";
    }


    private Long checkPrazo(Tarefa tarefa) {

        Long difDias = calcPrazo (tarefa);

        if(difDias < 0 || tarefa.getStatus () == StatusTarefa.FINALIZADA)
            return 0L;
        else
            return difDias;
    }




    public static List<ObterTarefaResponse> obterTarefaResposta(Page<Tarefa> tarefasPaginadas) {


        List<ObterTarefaResponse> tarefas = tarefasPaginadas
                .getContent ()
                .stream ()
                .map (tarefa -> {
                    //tarefa.setDataInicio (LocalDate.of(2024, 4, 30));
                    return ObterTarefaResponse
                            .builder ()
                            .id (tarefa.getId ())
                            .titulo (tarefa.getTitulo ())
                            .status (tarefa.getStatus ())
                            .dataPrevisao (formatarData (tarefa.getDataPrevisao ()))
                            .prazo (String.valueOf (tarefa.getPrazo () != null ? checkPrazo (tarefa) + " dias" : null))
                            .statusConformeTipo (formatDiasRestantes (tarefa))
                            .prioridade (tarefa.getPrioridade () != null ? tarefa.getPrioridade ().toString () : null)
                            .dataInicio (tarefa.getDataInicio().format (DATA))
                            .dataFim (formatarData (tarefa.getDataFim ()))
                            .dataAtualizacao (tarefa.getDataAtualizacao ().format (DATA_HORA))
                            .build ();
                })
                .toList ();

        return tarefas;
    }


    public static ObterTarefasPaginadasResponse obterTarefasPaginadasResponse (Page<Tarefa> tarefasPaginadas) {

        return ObterTarefasPaginadasResponse.builder ()
                .paginaAtual (tarefasPaginadas.getNumber ())
                .totalPaginas (tarefasPaginadas.getTotalPages ())
                .totalItens (tarefasPaginadas.getTotalElements ())
                .tarefas (obterTarefaResposta (tarefasPaginadas))
                .build ();
    }


    public static AtualizarStatusTarefaResponse atualizarStatusTarefaResponse(Tarefa statusTarefaAtualizada) {


        String dataFim = statusTarefaAtualizada.getDataFim () != null ?
                statusTarefaAtualizada.getDataFim ().format (DATA) :
                null;

        return AtualizarStatusTarefaResponse
                .builder ()
                .status (statusTarefaAtualizada.getStatus ().toString ())
                .dataInicio (statusTarefaAtualizada.getDataInicio ().format (DATA))
                .dataFim (dataFim)
                .build ();
    }

    public static AtualizarTarefaResponse atualizarTarefaResponse(Tarefa tarefaAtualizada) {
        return AtualizarTarefaResponse
                .builder ()
                .id (tarefaAtualizada.getId ())
                .titulo (tarefaAtualizada.getTitulo ())
                .statusTarefa (tarefaAtualizada.getStatus ().toString ())
                .dataAtualizacao (tarefaAtualizada.getDataAtualizacao ().format (DATA_HORA))
                .dataPrevisao (formatarData (tarefaAtualizada.getDataPrevisao ()))
                .prazo (tarefaAtualizada.getPrazo ())
                .prioridade (tarefaAtualizada.getPrioridade ().toString () != null ?
                        tarefaAtualizada.getPrioridade ().toString () :
                        null)
                .build ();
    }

}
