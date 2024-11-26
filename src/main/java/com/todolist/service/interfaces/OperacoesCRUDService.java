package com.todolist.service.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Interface responsável por conter métodos CRUD")
public interface OperacoesCRUDService<R, Request1, Request2> {

    R create(Request1 request);

    R update(Long id, Request2 request);

    void delete(Long id);

}
