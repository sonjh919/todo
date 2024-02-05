package com.sparta.todo.comment.dto;

import com.sparta.todo.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {

    private Long commentId;
    private String comment;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.comment = comment.getComment();
    }
}
