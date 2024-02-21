package com.sparta.todo.domain.todo.repository;

import com.sparta.todo.domain.todo.entity.TodoEntity;
import com.sparta.todo.domain.todo.model.Todo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoRepository {

    private final TodoJpaRepository todoJpaRepository;

    public Todo findTodoBy(Long id) {
        return Todo.from(todoJpaRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("선택한 일정은 존재하지 않습니다.")
        ));
    }

    public void save(TodoEntity todoEntity) {
        todoJpaRepository.save(todoEntity);
    }

    public void delete(Todo todo) {
        todoJpaRepository.delete(todo.toEntity());
    }

    public void update(Todo todo) {
        todoJpaRepository.saveAndFlush(todo.toEntity());
    }
}
