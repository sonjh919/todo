package com.sparta.todo.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용을 필수로 입력해야 합니다.")
    @Size(max = 512)
    private String comment;
}
