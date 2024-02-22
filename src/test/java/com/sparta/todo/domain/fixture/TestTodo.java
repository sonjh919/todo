package com.sparta.todo.domain.fixture;

import com.sparta.todo.domain.todo.model.Todo;
import java.time.LocalDateTime;

public class TestTodo extends Todo {

    public TestTodo(){
        super(
            1L,
            "타이틀",
            "내용",
            LocalDateTime.now(),
            true,
            true,
            new TestUser(1L, 1L).toEntity()
        );
    }

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
    
    public TestTodo(Long userId, Long todoId, String title){
        super(
            todoId,
            title,
            "내용",
            LocalDateTime.now(),
            true,
            true,
            new TestUser(userId, todoId).toEntity()
        );
    }



}
