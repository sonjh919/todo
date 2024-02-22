package com.sparta.todo.domain.fixture;

import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.domain.user.model.User;
import java.time.LocalDateTime;
import java.util.List;


public class TestUser extends User {

    public TestUser(){
        super(
            1L,
            "sjh",
            "12345678",
            List.of(new Todo(
                1L,
                "타이틀",
                "내용",
                LocalDateTime.now(),
                true,
                true,
                new UserEntity()
            ).toEntity())
        );
    }

    public TestUser(Long userId){
        super(
            userId,
            "sjh",
            "12345678",
            List.of(new Todo(
                1L,
                "타이틀",
                "내용",
                LocalDateTime.now(),
                true,
                true,
                new UserEntity()
            ).toEntity())
        );
    }

    public TestUser(Long userId, List<TestTodo> testTodos){
        super(
            userId,
            "sjh",
            "12345678",
            testTodos.stream()
                .map(Todo::toEntity).toList()
        );
    }

    public TestUser(Long userId, Long todoId){
        super(
            userId,
            "sjh",
            "12345678",
            List.of(new Todo(
                todoId,
                "타이틀",
                "내용",
                LocalDateTime.now(),
                true,
                true,
                new UserEntity()
            ).toEntity())
        );
    }
}
