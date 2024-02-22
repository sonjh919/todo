package com.sparta.todo.domain.todo.repository;

import com.sparta.todo.domain.todo.entity.TodoEntity;
import com.sparta.todo.domain.todo.model.Todo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


public interface TodoRepository {

    Todo findTodoBy(Long id);

    void save(TodoEntity todoEntity);

    void delete(Todo todo);

    void update(Todo todo);
}
