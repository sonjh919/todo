package com.sparta.todo.domain.comment.service;

import com.sparta.todo.domain.comment.dto.CommentRequestDto;
import com.sparta.todo.domain.comment.dto.CommentResponseDto;
import com.sparta.todo.domain.comment.entity.CommentEntity;
import com.sparta.todo.domain.comment.model.Comment;
import com.sparta.todo.domain.comment.repository.CommentRepository;
import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.user.model.User;
import com.sparta.todo.global.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final Validation validation;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(User user, Long todoId,
        CommentRequestDto requestDto) {

        Todo todo = validation.findTodoBy(todoId);

        CommentEntity commentEntity = new CommentEntity(requestDto, todo, user);
        commentRepository.save(commentEntity);

        return Comment.from(commentEntity).responseDto();
    }

    public CommentResponseDto updateComment(User user, Long todoId, Long commentId,
        CommentRequestDto requestDto) {

        Comment comment = validation.findCommentBy(commentId);
        Todo todo = validation.findTodoBy(todoId);

        comment.validateBy(todo);
        comment.validateBy(user);

        comment.update(requestDto);
        commentRepository.update(comment);

        return comment.responseDto();
    }

    public void deleteComment(Long todoId, Long commentId) {
        Comment comment = validation.findCommentBy(commentId);

        validation.findTodoBy(todoId);

        commentRepository.delete(comment);
    }

}
