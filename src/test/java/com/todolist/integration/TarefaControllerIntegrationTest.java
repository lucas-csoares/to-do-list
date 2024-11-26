package com.todolist.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.ToDoListApplication;
import com.todolist.entity.Tarefa;
import com.todolist.request.AtualizarTarefaRequest;
import com.todolist.request.CreateTarefaRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.format.DateTimeFormatter;

import static com.todolist.mock.TarefaMock.*;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {ToDoListApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Schema(description = "Classe de teste de integração para a aplicação ToDoList que segue uma ordem específica")
public class TarefaControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long idDaTarefaCriada;

    private CreateTarefaRequest createTarefaRequest;

    private AtualizarTarefaRequest atualizarTarefaRequest;

    @BeforeEach
    @Operation(description = "Método que deve ser executado antes de cada método de teste", summary = "Configura o RestAssured para usar a URL base http://localhost e a porta aleatória injetada")
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        createTarefaRequest = tarefaComDataPrevisao();
        atualizarTarefaRequest = atualizarTarefaComDataPrevisao();

    }


    @Test
    @Order (1)
    @Operation(description = " Faz uma requisição GET para '/api/tarefa' e verifica se o status de resposta é 200 " +
            "(OK).")
    public void testarGetTodasAsTarefasStatus200() {
        get("/api/tarefa").then().statusCode(200);
    }

    @Test
    @Operation(description = " Faz uma requisição GET para 'api/tarefa?titulo=Primeira tarefa'", summary = "Verifica " +
            "se o status é 200 e se O corpo da resposta contém uma tarefa cujo título é 'Primeira tarefa'")
    @Order(3)
    public void testarGetTarefaPorTitulo() {
        get("/api/tarefa?titulo=" + createTarefaRequest.getTitulo()).then().statusCode(200)
                .assertThat().body("tarefas[0].titulo", equalTo(createTarefaRequest.getTitulo()));
    }



    @Test
    @Operation(description = "Faz uma requisição POST para criar uma nova tarefa e verifica se o status de resposta é 201 (Created) e se o corpo da resposta contém a tarefa criada com o título, prazo e prioridade corretos.")
    @Order(2)
    public void testarCriacaoDeTarefa() throws Exception {
        String novaTarefaJson = objectMapper.writeValueAsString(createTarefaRequest);

        Response response = given()
                .contentType("application/json")
                .body(novaTarefaJson)
                .when()
                .post("/api/tarefa")
                .then()
                .log().ifError()
                .statusCode(201)
                .extract()
                .response();

        assertNotNull(response);
        idDaTarefaCriada = response.jsonPath().getLong("id");

        assertEquals(createTarefaRequest.getTitulo(), response.jsonPath().getString("titulo"));
        String dataPrevisaoEsperada = createTarefaRequest.getDataPrevisao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        assertEquals(dataPrevisaoEsperada, response.jsonPath().getString("dataPrevisao"));
        assertEquals(createTarefaRequest.getPrioridade().name(), response.jsonPath().getString("prioridade"));


    }


    @Test
    @Operation(description = "Faz uma requisição PATCH para atualizar o status de uma tarefa para finalizada e verifica se o status de resposta é 200 (OK) e se o corpo da resposta contém o status atualizado corretamente.")
    @Order(5)
    public void testarAtualizacaoStatusTarefa() {

        // Realiza a requisição de atualização de status e armazena a resposta
        Response response = given()
                .contentType("application/json")
                .when()
                .patch("/api/tarefa/{id}/status", idDaTarefaCriada)
                .then()
                .statusCode(200) // Verifica o status code
                .extract()
                .response();

        // Verificação de conteúdo da resposta
        String contentType = response.getContentType();
        assertEquals("application/json", contentType, "O tipo de conteúdo da resposta deve ser 'application/json'.");

    }

    @Test
    @Operation(description = "Faz uma requisição DELETE para deletar uma tarefa pelo ID e verifica se o status de resposta é o esperado.")
    @Order(6)
    public void testarDeletarTarefa() {

        given()
                .pathParam("id", idDaTarefaCriada)
                .when()
                .delete("/api/tarefa/{id}")
                .then()
                .statusCode(204);

        Long tarefaInexistenteId = 1000L;

        given()
                .pathParam("id", tarefaInexistenteId)
                .when()
                .delete("/api/tarefa/{id}")
                .then()
                .statusCode(404);
    }


    @Test
    @Operation(description = "Faz uma requisição PUT para atualizar uma tarefa e verifica se a atualização foi bem-sucedida.")
    @Order(4)
    public void testarAtualizarTarefa() throws JsonProcessingException {
        String tarefaAtualizada = objectMapper.writeValueAsString(atualizarTarefaRequest);
        given()
                .contentType("application/json")
                .body(tarefaAtualizada)
                .pathParam("id", idDaTarefaCriada)
                .when()
                .put("/api/tarefa/{id}")
                .then()
                .log().ifError()
                .statusCode(200)
                .body("titulo", equalTo(atualizarTarefaRequest.getTitulo()))
                .body("dataPrevisao",
                        equalTo(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(atualizarTarefaRequest.getDataPrevisao())))
                .body("prioridade", equalTo(atualizarTarefaRequest.getPrioridade().name()));
    }



}
