package com.sparta.todo.domain.todo.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TodoRequestDto {

    @NotBlank(message = "제목을 필수로 입력해야 합니다.")
    @Size(max = 50)
    private String title;

    @NotBlank(message = "내용을 필수로 입력해야 합니다.")
    @Size(max = 1024)
    private String content;

}
