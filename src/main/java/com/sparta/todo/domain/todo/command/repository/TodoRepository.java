package com.sparta.todo.domain.todo.command.repository;

import com.sparta.todo.domain.todo.model.TodoEntity;
import com.sparta.todo.domain.todo.model.Todo;


public interface TodoRepository {

    Todo findTodoBy(Long id);

    void save(TodoEntity todoEntity);

    void delete(Todo todo);

    void update(Todo todo);
}
