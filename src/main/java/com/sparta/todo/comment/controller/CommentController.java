package com.sparta.todo.comment.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.sparta.todo.comment.dto.CommentRequestDto;
import com.sparta.todo.comment.dto.CommentResponseDto;
import com.sparta.todo.comment.service.CommentService;
import com.sparta.todo.common.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("v1/todos/{todoId}/comments")
    @Operation(summary = "댓글 작성")
    public ResponseEntity<ResponseDto> createComment(
        @RequestHeader(value = "Authorization") String accessToken,
        @PathVariable Long todoId,
        @RequestBody @Valid CommentRequestDto requestDto) {
        log.info("댓글 작성 API");

        CommentResponseDto commentResponseDto = commentService.createComment(accessToken, todoId, requestDto);
        URI createdUri = linkTo(
            methodOn(CommentController.class).createComment(accessToken, todoId, requestDto)).slash(
            commentResponseDto.getCommentId()).toUri();

        return ResponseEntity.created(createdUri)
            .body(new ResponseDto("댓글 작성 성공", commentResponseDto));
    }

    @PatchMapping("v1/todos/{todoId}/comments/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<ResponseDto> updateComment(
        @RequestHeader(value = "Authorization") String accessToken,
        @PathVariable Long todoId,
        @PathVariable Long commentId,
        @RequestBody @Valid CommentRequestDto requestDto
    ) {
        log.info("할일카드 수정 API");

        CommentResponseDto commentResponseDto = commentService.updateComment(accessToken, todoId, commentId, requestDto);
        URI createdUri = linkTo(
            methodOn(CommentController.class).updateComment(accessToken, todoId, commentId, requestDto)).slash(
            commentResponseDto.getCommentId()).toUri();

        return ResponseEntity.created(createdUri)
            .body(new ResponseDto("할일카드 수정 성공", commentResponseDto));
    }

    @DeleteMapping("v1/todos/{todoId}/comments/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<ResponseDto> deleteTodo(
        @RequestHeader(value = "Authorization") String accessToken,
        @PathVariable Long todoId,
        @PathVariable Long commentId
    ){
        log.info("댓글 삭제 API");

        commentService.deleteComment(accessToken, todoId, commentId);

        return ResponseEntity.noContent().build();
    }

}
