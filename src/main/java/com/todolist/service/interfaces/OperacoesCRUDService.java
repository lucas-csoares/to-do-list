package com.todolist.service.interfaces;

public interface OperacoesCRUDService<R, P1, P2> {

    R create(P1 request);

    R update(Long id, P2 response);

    void delete(Long id);

}
