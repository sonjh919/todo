package com.sparta.todo.comment.controller;

import static com.sparta.todo.message.CommentMessage.CREATE_COMMENT_API;
import static com.sparta.todo.message.CommentMessage.CREATE_COMMENT_SUCCESS;
import static com.sparta.todo.message.CommentMessage.DELETE_COMMENT_API;
import static com.sparta.todo.message.CommentMessage.PATCH_COMMENT_API;
import static com.sparta.todo.message.CommentMessage.PATCH_COMMENT_SUCCESS;

import com.sparta.todo.comment.dto.CommentRequestDto;
import com.sparta.todo.comment.dto.CommentResponseDto;
import com.sparta.todo.comment.service.CommentService;
import com.sparta.todo.commonDto.ResponseDto;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("v1/todos/{todoId}/comments")
    @Operation(summary = CREATE_COMMENT_API)
    public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(
        @RequestHeader(value = "Authorization") String accessToken,
        @PathVariable Long todoId,
        @RequestBody @Valid CommentRequestDto requestDto) {
        log.info(CREATE_COMMENT_API);

        CommentResponseDto commentResponseDto = commentService.createComment(accessToken, todoId,
            requestDto);

        return ResponseEntity.created(createUri(commentResponseDto.getCommentId()))
            .body(ResponseDto.<CommentResponseDto>builder()
                .message(CREATE_COMMENT_SUCCESS)
                .data(commentResponseDto)
                .build());
    }

    @PatchMapping("v1/todos/{todoId}/comments/{commentId}")
    @Operation(summary = PATCH_COMMENT_API)
    public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(
        @RequestHeader(value = "Authorization") String accessToken,
        @PathVariable Long todoId,
        @PathVariable Long commentId,
        @RequestBody @Valid CommentRequestDto requestDto
    ) {
        log.info(PATCH_COMMENT_API);

        CommentResponseDto commentResponseDto = commentService.updateComment(accessToken, todoId,
            commentId, requestDto);

        return ResponseEntity.created(updateUri())
            .body(ResponseDto.<CommentResponseDto>builder()
                .message(PATCH_COMMENT_SUCCESS)
                .data(commentResponseDto)
                .build());
    }

    @DeleteMapping("v1/todos/{todoId}/comments/{commentId}")
    @Operation(summary = DELETE_COMMENT_API)
    public ResponseEntity<Void> deleteTodo(
        @RequestHeader(value = "Authorization") String accessToken,
        @PathVariable Long todoId,
        @PathVariable Long commentId
    ) {
        log.info(DELETE_COMMENT_API);

        commentService.deleteComment(accessToken, todoId, commentId);

        return ResponseEntity.noContent().build();
    }

    private URI createUri(Long commentId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(commentId)
            .toUri();
    }

    private URI updateUri() {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .replaceQueryParam("isCompleted")
            .replaceQueryParam("isPrivate")
            .build()
            .toUri();
    }

}
