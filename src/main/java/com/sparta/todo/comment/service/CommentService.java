package com.sparta.todo.comment.service;

import com.sparta.todo.comment.dto.CommentRequestDto;
import com.sparta.todo.comment.dto.CommentResponseDto;
import com.sparta.todo.comment.entity.Comment;
import com.sparta.todo.comment.repository.CommentRepository;
import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.repository.TodoRepository;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.repository.UserRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final JwtUtil jwtUtil;

    public CommentResponseDto createComment(String accessToken, Long todoId,
        CommentRequestDto requestDto) {

        String author = jwtUtil.getUserInfoFromToken(accessToken);

        User user = userRepository.findByUserName(author)
            .orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));
        Todo todo = todoRepository.findById(todoId)
            .orElseThrow(() -> new NoSuchElementException("해당 할일카드가 존재하지 않습니다."));

        Comment comment = new Comment(requestDto, todo, user);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    public CommentResponseDto updateComment(String accessToken, Long todoId, Long commentId, CommentRequestDto requestDto) {
        String author = jwtUtil.getUserInfoFromToken(accessToken);
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new NoSuchElementException("해당 댓글은 존재하지 않습니다.")
        );

        todoRepository.findById(todoId).orElseThrow(
            () -> new NoSuchElementException("해당 할일카드가 존재하지 않습니다.")
        );

        if(author.equals(comment.getUser().getUserName())) {
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        }
        throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
    }


    public void deleteComment(String accessToken, Long todoId, Long commentId) {
        jwtUtil.getUserInfoFromToken(accessToken);
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new NoSuchElementException("해당 댓글은 존재하지 않습니다.")
        );

        todoRepository.findById(todoId).orElseThrow(
            () -> new NoSuchElementException("해당 할일카드가 존재하지 않습니다.")
        );

        commentRepository.delete(comment);


    }
}
