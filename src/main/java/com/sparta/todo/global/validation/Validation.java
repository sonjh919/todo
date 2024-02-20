package com.sparta.todo.global.validation;

import com.sparta.todo.domain.comment.entity.Comment;
import com.sparta.todo.domain.comment.repository.CommentRepository;
import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.todo.repository.TodoJpaRepository;
import com.sparta.todo.domain.user.model.User;
import com.sparta.todo.domain.user.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validation {

    private final UserJpaRepository userJpaRepository;
    private final TodoJpaRepository todoJpaRepository;
    private final CommentRepository commentRepository;

    public User userBy(String userName) {
        return User.from(userJpaRepository.findByUserName(userName).orElseThrow(
            () -> new NoSuchElementException("사용자를 찾을 수 없습니다.")
        ));
    }

    public Todo findTodoBy(Long id) {
        return Todo.from(todoJpaRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("선택한 일정은 존재하지 않습니다.")
        ));
    }

    public Comment findCommentBy(Long id) {
        return commentRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("해당 댓글은 존재하지 않습니다.")
        );
    }


}
