package com.todolist.integration;

import com.todolist.ToDoListApplication;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {ToDoListApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class TarefaControllerIntegrationTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.port = 8080;
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() { get("/api/tarefa").then().statusCode(200);
    }


    @Test public void givenUrl_whenSuccessOnGetsResponseAndJsonHasOneTask_thenCorrect() { get("/api/tarefa?titulo" +
            "=Primeira tarefa").then().statusCode(200) .assertThat().body("titulo", equalTo("Primeira tarefa")); }






}
