package com.sparta.todo.todo.repository;

import com.sparta.todo.todo.entity.Todo;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
