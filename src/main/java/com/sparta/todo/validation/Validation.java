package com.sparta.todo.validation;

import com.sparta.todo.comment.entity.Comment;
import com.sparta.todo.comment.repository.CommentRepository;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.repository.TodoRepository;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.repository.UserRepository;
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
        return userRepository.findByUserName(userName).orElseThrow(
            () -> new NoSuchElementException("사용자를 찾을 수 없습니다.")
        );
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
