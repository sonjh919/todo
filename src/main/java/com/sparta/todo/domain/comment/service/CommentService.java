package com.sparta.todo.domain.comment.service;

import com.sparta.todo.domain.comment.dto.CommentRequestDto;
import com.sparta.todo.domain.comment.dto.CommentResponseDto;
import com.sparta.todo.domain.comment.entity.CommentEntity;
import com.sparta.todo.domain.comment.model.Comment;
import com.sparta.todo.domain.comment.repository.CommentRepository;
import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.todo.command.repository.TodoRepository;
import com.sparta.todo.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(User user, Long todoId,
        CommentRequestDto requestDto) {

        Todo todo = todoRepository.findTodoBy(todoId);

        CommentEntity commentEntity = new CommentEntity(requestDto, todo, user);
        commentRepository.save(commentEntity);

        return Comment.from(commentEntity).responseDto();
    }

    public CommentResponseDto updateComment(User user, Long todoId, Long commentId,
        CommentRequestDto requestDto) {

        Comment comment = commentRepository.findCommentBy(commentId);
        Todo todo = todoRepository.findTodoBy(todoId);

        comment.validateBy(todo);
        comment.validateBy(user);

        comment.update(requestDto);
        commentRepository.update(comment);

        return comment.responseDto();
    }

    public void deleteComment(User user, Long todoId, Long commentId) {
        Comment comment = commentRepository.findCommentBy(commentId);

        Todo todo = todoRepository.findTodoBy(todoId);
        comment.validateBy(user);
        comment.validateBy(todo);

        commentRepository.delete(comment);
    }

}
