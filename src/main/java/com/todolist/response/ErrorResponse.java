package com.todolist.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@Schema(description = "Todos os detalhes da resposta a um erro lançado")
public class ErrorResponse {
    private String status;
    private List<Map<String, String>> erros;
}
