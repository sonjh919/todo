package com.sparta.todo.domain.todo.query.controller;

import static com.sparta.todo.global.message.TodoMessage.GET_TODO_API;
import static com.sparta.todo.global.message.TodoMessage.GET_TODO_SUCCESS;
import static com.sparta.todo.global.message.TodoMessage.SEARCH_TODOS_API;
import static com.sparta.todo.global.message.TodoMessage.SEARCH_TODOS_DESCRIPTION;
import static com.sparta.todo.global.message.TodoMessage.SEARCH_TODOS_SUCCESS;

import com.sparta.todo.domain.todo.query.dto.GetTodoListResponseDto;
import com.sparta.todo.domain.todo.query.dto.GetTodoResponseDto;
import com.sparta.todo.domain.todo.query.service.TodoQueryServiceImpl;
import com.sparta.todo.global.commonDto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoQueryController {
    private final TodoQueryServiceImpl getTodoService;
    @GetMapping("v2/todos/{id}")
    @Operation(summary = GET_TODO_API)
    public ResponseEntity<ResponseDto<GetTodoResponseDto>> getTodoById(@PathVariable Long id) {

        return ResponseEntity.ok()
            .body(ResponseDto.<GetTodoResponseDto>builder()
                .message(GET_TODO_SUCCESS)
                .data(getTodoService.getTodoById(id))
                .build());
    }

    @GetMapping("v2/todos")
    @Operation(summary = SEARCH_TODOS_API, description = SEARCH_TODOS_DESCRIPTION)
    public ResponseEntity<ResponseDto<List<GetTodoResponseDto>>> getTodos(
        @RequestParam(required = false) String title) {

        return ResponseEntity.ok()
            .body(ResponseDto.<List<GetTodoResponseDto>>builder()
                .message(SEARCH_TODOS_SUCCESS)
                .data(getTodoService.getTodos(title))
                .build());
    }
}
