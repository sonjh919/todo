package com.sparta.todo.comment.service;

import com.sparta.todo.comment.dto.CommentRequestDto;
import com.sparta.todo.comment.dto.CommentResponseDto;
import com.sparta.todo.comment.entity.Comment;
import com.sparta.todo.comment.repository.CommentRepository;
import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final Validation validation;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    public CommentResponseDto createComment(String accessToken, Long todoId,
        CommentRequestDto requestDto) {

        String author = jwtUtil.getUserInfoFromToken(accessToken);

        User user = validation.userBy(author);
        Todo todo = validation.findTodoBy(todoId);

        Comment comment = new Comment(requestDto, todo, user);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    public CommentResponseDto updateComment(String accessToken, Long todoId, Long commentId, CommentRequestDto requestDto) {
        String author = jwtUtil.getUserInfoFromToken(accessToken);
        Comment comment = validation.findCommentBy(commentId);

        validation.findTodoBy(todoId);

        if(author.equals(comment.getUser().getUserName())) {
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        }
        throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
    }


    public void deleteComment(String accessToken, Long todoId, Long commentId) {
        jwtUtil.getUserInfoFromToken(accessToken);

        Comment comment = validation.findCommentBy(commentId);
        validation.findTodoBy(todoId);

        commentRepository.delete(comment);


    }
}
