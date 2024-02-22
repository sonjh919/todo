package com.sparta.todo.domain.fixture;

import com.sparta.todo.domain.comment.model.Comment;

public class TestComment extends Comment {
    public TestComment(Long commentId, Long userId, Long todoId){
        super(
            commentId,
            new TestUser(userId, todoId).toEntity(),
            new TestTodo(userId, todoId).toEntity(),
            "댓글"
        );
    }

}
