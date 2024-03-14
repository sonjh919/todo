package com.sparta.todo.domain.todo.command.repository;

import com.sparta.todo.domain.todo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJpaRepository extends JpaRepository<TodoEntity, Long> {

}
