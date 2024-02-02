package com.sparta.todo.todo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.sparta.todo.common.ResponseDto;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.dto.CreateTodoResponseDto;
import com.sparta.todo.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@EnableJpaAuditing
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("v1/todos")
    @Operation(summary = "할일카드 작성")
    public ResponseEntity<ResponseDto> createTodo(
        @RequestHeader(value = "Authorization") String accessToken,
        @RequestBody @Valid TodoRequestDto requestDto) {
        log.info("할일카드 작성 API");

        CreateTodoResponseDto todoResponseDto = todoService.createTodo(accessToken, requestDto);
        URI createdUri = linkTo(
            methodOn(TodoController.class).createTodo(accessToken, requestDto)).slash(
            todoResponseDto.getTodoId()).toUri();

        return ResponseEntity.created(createdUri)
            .body(new ResponseDto("할일카드 작성 성공", todoResponseDto));
    }

    @GetMapping("v1/todos/{id}")
    @Operation(summary = "할일카드 조회")
    public ResponseEntity<ResponseDto> getTodoById(@PathVariable Long id) {
        log.info("할일카드 조회 API");

        return ResponseEntity.ok().body(new ResponseDto("할일카드 조회 성공", todoService.getTodoById(id)));
    }

    @GetMapping("v1/todos")
    @Operation(summary = "할일카드 전체 목록 조회 및 검색",
        description = "title 정보가 없으면 할일카드 전체의 목록을 조회합니다. title 정보가 포함되어 있다면, title이 일치하는 할일카드들을 검색하여 결과를 반환합니다.")
    public ResponseEntity<ResponseDto> getTodos(@RequestParam(required = false) String title) {
        log.info("할일카드 전체 목록 조회 및 검색 API");

        return ResponseEntity.ok()
            .body(new ResponseDto("할일카드 목록 조회 성공", todoService.getTodos(title)));
    }


}