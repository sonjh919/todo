package com.sparta.todo.domain.fixture;

import com.sparta.todo.domain.comment.model.Comment;
import com.sparta.todo.domain.todo.entity.TodoEntity;
import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.user.entity.UserEntity;

public class TestComment extends Comment {
    public TestComment(){
        super(
            1L,
            new TestUser(1L, 1L).toEntity(),
            new TestTodo(1L, 1L).toEntity(),
            "댓글"
        );
    }

}
