package com.sparta.todo.domain.comment.controller;

import static com.sparta.todo.global.message.CommentMessage.CREATE_COMMENT_API;
import static com.sparta.todo.global.message.CommentMessage.CREATE_COMMENT_SUCCESS;
import static com.sparta.todo.global.message.CommentMessage.DELETE_COMMENT_API;
import static com.sparta.todo.global.message.CommentMessage.PATCH_COMMENT_API;
import static com.sparta.todo.global.message.CommentMessage.PATCH_COMMENT_SUCCESS;

import com.sparta.todo.domain.comment.dto.CommentRequestDto;
import com.sparta.todo.domain.comment.dto.CommentResponseDto;
import com.sparta.todo.domain.comment.service.CommentService;
import com.sparta.todo.domain.user.model.User;
import com.sparta.todo.global.commonDto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("v1/todos/{todoId}/comments")
    @Operation(summary = CREATE_COMMENT_API)
    public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(
        @RequestAttribute("User") User user,
        @PathVariable Long todoId,
        @RequestBody @Valid CommentRequestDto requestDto) {

        CommentResponseDto commentResponseDto = commentService.createComment(user, todoId,
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
        @RequestAttribute("User") User user,
        @PathVariable Long todoId,
        @PathVariable Long commentId,
        @RequestBody @Valid CommentRequestDto requestDto
    ) {

        CommentResponseDto commentResponseDto = commentService.updateComment(user, todoId,
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
        @PathVariable Long todoId,
        @PathVariable Long commentId
    ) {

        commentService.deleteComment(todoId, commentId);

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
