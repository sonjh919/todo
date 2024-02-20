package com.sparta.todo.global.validation;

import com.sparta.todo.domain.comment.entity.Comment;
import com.sparta.todo.domain.comment.repository.CommentRepository;
import com.sparta.todo.domain.todo.entity.Todo;
import com.sparta.todo.domain.todo.repository.TodoRepository;
import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.domain.user.model.User;
import com.sparta.todo.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validation {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    public User userBy(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName).orElseThrow(
            () -> new NoSuchElementException("사용자를 찾을 수 없습니다.")
        );

        return User.from(userEntity);
    }

    public Todo findTodoBy(Long id) {
        return todoRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("선택한 일정은 존재하지 않습니다.")
        );
    }

    public Comment findCommentBy(Long id) {
        return commentRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("해당 댓글은 존재하지 않습니다.")
        );
    }


}
