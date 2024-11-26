package com.todolist.unit.controller;

import com.todolist.entity.Tarefa;
import com.todolist.request.AtualizarTarefaRequest;
import com.todolist.request.CreateTarefaRequest;
import com.todolist.service.TarefaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static com.todolist.mock.TarefaMock.*;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TarefaService tarefaService;

    private Tarefa tarefaComDataPrevisao;

    private AtualizarTarefaRequest atualizarTarefaComDataPrevisao;

    private Tarefa tarefaMockComPrazo;


    @BeforeEach
    void setUp() {
        tarefaComDataPrevisao = obterTarefaMockComDataPrevisao ();
        tarefaMockComPrazo = obterTarefaMockComPrazo ();
        atualizarTarefaComDataPrevisao = atualizarTarefaComDataPrevisao();
    }

    @Test
    @DisplayName("post: retornar objeto tarefa criado")
    void post_taskIsCreated_returnCreatedTask() throws Exception {

        // Isolando a camada controller
        given(tarefaService.create(any(CreateTarefaRequest.class)))
                .willReturn(tarefaMockComPrazo);


        ResultActions apiResponse = mockMvc.perform(post("/tarefa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tarefaComDataPrevisao)));


        apiResponse.andExpect(status().isCreated());
    }


    @Test
    @DisplayName("update: retornar tarefa atualizada")
    void update_taskUpdated_returnTaskUpdated() throws Exception {
        Long tarefaId = tarefaComDataPrevisao.getId ();

        given(tarefaService.update (eq(tarefaId) ,any(AtualizarTarefaRequest.class)))
                .willReturn(tarefaComDataPrevisao);

        ResultActions apiResponse = mockMvc.perform(put("/tarefa/" + tarefaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(atualizarTarefaComDataPrevisao)));

        apiResponse.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tarefaComDataPrevisao.getId()))
                .andExpect(jsonPath("$.titulo").value(tarefaComDataPrevisao.getTitulo()))
                .andExpect(jsonPath("$.prazo").value(tarefaComDataPrevisao.getPrazo()))
                .andExpect(jsonPath("$.prioridade").value(tarefaComDataPrevisao.getPrioridade().toString()));
    }


    @Test
    @DisplayName("updateStatus: retorna a tarefa de status atualizado")
    void updateStatus_taskStatusUpdated_returnUpdatedTaskStatus() throws Exception {
        Long tarefaId = tarefaComDataPrevisao.getId ();


        given(tarefaService.updateStatus(anyLong()))
                .willReturn(tarefaComDataPrevisao);

        ResultActions apiResponse = mockMvc.perform(patch("/tarefa/" + tarefaId + "/status").contentType(MediaType.APPLICATION_JSON));

        apiResponse.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(tarefaComDataPrevisao.getStatus().toString()));
    }


    @Test
    @DisplayName("delete: deleta uma tarefa e não tem retorno")
    void delete_taskIsDeleted_returnNoContent() throws Exception {
        Long tarefaId = tarefaComDataPrevisao.getId ();

        doNothing().when(tarefaService).delete(anyLong());

        ResultActions apiResponse = mockMvc.perform(delete("/tarefa/" + tarefaId)
                .contentType(MediaType.APPLICATION_JSON));

        apiResponse.andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("findByTarefa: retorna tarefas paginadas quando o título não é dado")
    void findByTarefa_noTitleProvided_returnsPaginatedTasks() throws Exception {
        Page<Tarefa> tarefasPaginadas = new PageImpl<>(Collections.singletonList(tarefaMockComPrazo));

        given(tarefaService.findAll(any(Pageable.class)))
                .willReturn(tarefasPaginadas);

        ResultActions apiResponse = mockMvc.perform(get("/tarefa")
                .param("page", "0")
                .param("size", "5")
                .contentType(MediaType.APPLICATION_JSON));

        apiResponse.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tarefas[0].titulo").value(tarefaMockComPrazo.getTitulo()));
    }



    @Test
    @DisplayName("findByTarefa: retorna tarefas paginadas quando o título é fornecido")
    void findByTarefa_titleProvided_returnsPaginatedTasks() throws Exception {
        // Given
        String titulo = tarefaMockComPrazo.getTitulo ();
        Page<Tarefa> tarefasPaginadas = new PageImpl<>(Collections.singletonList(tarefaMockComPrazo));

        given(tarefaService.findByTitulo(titulo, PageRequest.of(0, 5)))
                .willReturn(tarefasPaginadas);


        ResultActions apiResponse = mockMvc.perform(get("/tarefa")
                .param("titulo", titulo)
                .param("page", "0")
                .param("size", "5")
                .contentType(MediaType.APPLICATION_JSON));


        apiResponse.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tarefas[0].titulo").value(tarefaMockComPrazo.getTitulo()));
    }


}
