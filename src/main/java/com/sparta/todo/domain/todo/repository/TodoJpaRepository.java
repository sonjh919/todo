package com.sparta.todo.domain.todo.repository;

import com.sparta.todo.domain.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJpaRepository extends JpaRepository<TodoEntity, Long> {

}
