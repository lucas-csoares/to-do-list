package com.todolist;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Schema(description = "classe de teste inicial que verifica se a aplicação pode ser carregada sem erros graves.")
class ToDoListApplicationTests {

	@Test
	@Operation(description = "verifica se o contexto da aplicação Spring Boot é carregado corretamente")
	void contextLoads() {
	}

}
