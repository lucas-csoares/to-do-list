package com.todolist.integration;

import com.todolist.ToDoListApplication;
import io.restassured.RestAssured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {ToDoListApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Schema(description = "Classe de teste de integração para a aplicação ToDoList")
public class TarefaControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    @Operation(description = "Método que deve ser executado antes de cada método de teste", summary = "Configura o RestAssured para usar a URL base http://localhost e a porta aleatória injetada")
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }


    @Test
    @Operation(description = " Faz uma requisição GET para '/api/tarefa' e verifica se o status de resposta é 200 " +
            "(OK).")
    public void testarGetTodasAsTarefasStatus200() {
        get("/api/tarefa").then().statusCode(200);
    }

    @Test
    @Operation(description = " Faz uma requisição GET para 'api/tarefa?titulo=Primeira tarefa'", summary = "Verifica " +
            "se o status é 200 e se O corpo da resposta contém uma tarefa cujo título é 'Primeira tarefa'")
    public void testarGetTarefaPorTitulo() {
        get("/api/tarefa?titulo=Primeira tarefa").then().statusCode(200)
                .assertThat().body("tarefas[0].titulo", equalTo("Primeira tarefa"));
    }



    @Test
    @Operation(description = "Faz uma requisição POST para criar uma nova tarefa e verifica se o status de resposta é 201 (Created) e se o corpo da resposta contém a tarefa criada com o título, prazo e prioridade corretos.")
    public void testarCriacaoDeTarefa() {
        String novaTarefaJson = """
            {
              "titulo": "Tarefa para testar verbo POST",
              "prazo": 10,
              "prioridade": "ALTA"
            }
        """;

        given()
                .contentType("application/json")
                .body(novaTarefaJson)
                .when()
                .post("/api/tarefa")
                .then()
                .log().ifError() //Mostra detalhes de erros
                .statusCode(201)
                .assertThat()
                .body("titulo", equalTo("Tarefa para testar verbo POST"))
                .body("prazo", equalTo("10 dias"))
                .body("prioridade", equalTo("ALTA"));
    }


    @Test
    @Operation(description = "Faz uma requisição PATCH para atualizar o status de uma tarefa para finalizada e verifica se o status de resposta é 200 (OK) e se o corpo da resposta contém o status atualizado corretamente.")
    public void testarAtualizacaoStatusTarefa() {
        Long tarefaId = 8L;

        given()
                .contentType("application/json")
                .when()
                .patch("/api/tarefa/{id}/status", tarefaId)
                .then()
                .statusCode(200)
                .assertThat()
                .body("status", equalTo("FINALIZADA"));
    }

    @Test
    @Operation(description = "Faz uma requisição DELETE para deletar uma tarefa pelo ID e verifica se o status de resposta é o esperado.")
    public void testarDeletarTarefa() {
        Long tarefaId = 4L;

        given()
                .pathParam("id", tarefaId)
                .when()
                .delete("/api/tarefa/{id}")
                .then()
                .statusCode(204); // Status esperado: No Content

        Long tarefaInexistenteId = 1000L;

        given()
                .pathParam("id", tarefaInexistenteId)
                .when()
                .delete("/api/tarefa/{id}")
                .then()
                .statusCode(404); // Status esperado: Not Found


        Long tarefaNaoDeletavelId = 8L; // ID de uma tarefa que não pode ser deletada

        given()
                .pathParam("id", tarefaNaoDeletavelId)
                .when()
                .delete("/api/tarefa/{id}")
                .then()
                .statusCode(422); // Status esperado: Unprocessable Entity
    }


    @Test
    @Operation(description = "Faz uma requisição PUT para atualizar uma tarefa e verifica se a atualização foi bem-sucedida.")
    public void testarAtualizarTarefa() {

        Long tarefaId = 1L;

        String requestBody = """
        {
            "titulo": "Tarefa atualizada para teste pelo Junit",
            "dataPrevisao": "30/05/2024",
                "prioridade": "BAIXA"
        }
    """;

        given()
                .contentType("application/json")
                .body(requestBody)
                .pathParam("id", tarefaId)
                .when()
                .put("/api/tarefa/{id}")
                .then()
                .log().ifError() // logar o que houve de errado
                .statusCode(200)
                .body("titulo", equalTo("Tarefa atualizada para teste pelo Junit"))
                .body("dataPrevisao", equalTo("30/05/2024"))
                .body("prioridade", equalTo("BAIXA"));
    }



}
