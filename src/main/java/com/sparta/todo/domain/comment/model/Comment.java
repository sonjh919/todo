package com.sparta.todo.domain.comment.model;

import com.sparta.todo.domain.comment.dto.CommentRequestDto;
import com.sparta.todo.domain.comment.dto.CommentResponseDto;
import com.sparta.todo.domain.comment.entity.CommentEntity;
import com.sparta.todo.domain.todo.entity.TodoEntity;
import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.domain.user.model.User;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;

@AllArgsConstructor
public class Comment {

    private Long commentId;
    private UserEntity userEntity;
    private TodoEntity todoEntity;
    private String comment;

    public static Comment from(final CommentEntity commentEntity) {
        return new Comment(
            commentEntity.getCommentId(),
            commentEntity.getUserEntity(),
            commentEntity.getTodoEntity(),
            commentEntity.getComment()
        );
    }

    public CommentEntity toEntity() {
        return new CommentEntity(
            commentId,
            userEntity,
            todoEntity,
            comment
        );
    }

    public CommentResponseDto responseDto() {
        return new CommentResponseDto(
            commentId,
            comment
        );
    }

    public void validateBy(Todo todo) {
        if (!todo.validateById(todoEntity.getTodoId())) {
            throw new NoSuchElementException("할일카드에 해당 댓글이 존재하지 않습니다.");
        }
    }

    public void validateBy(User user) {
        if (!user.validateById(userEntity.getUserId())) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }
    }


    public void update(CommentRequestDto requestDto) {
        comment = requestDto.getComment();
    }
}
