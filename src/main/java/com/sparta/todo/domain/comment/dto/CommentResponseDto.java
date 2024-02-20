package com.sparta.todo.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentResponseDto {

    private Long commentId;
    private String comment;

}
