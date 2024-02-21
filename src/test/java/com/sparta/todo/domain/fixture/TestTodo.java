package com.sparta.todo.domain.fixture;

import com.sparta.todo.domain.todo.model.Todo;
import java.time.LocalDateTime;

public class TestTodo extends Todo {
    
    public TestTodo(Long userId, Long todoId){
        super(
            todoId,
            "타이틀",
            "내용",
            LocalDateTime.now(),
            true,
            true,
            new TestUser(userId, todoId).toEntity()
        );
    }



}
