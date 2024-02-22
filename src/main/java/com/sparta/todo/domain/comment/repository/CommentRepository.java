package com.sparta.todo.domain.comment.repository;

import com.sparta.todo.domain.comment.entity.CommentEntity;
import com.sparta.todo.domain.comment.model.Comment;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


public interface CommentRepository {
    Comment findCommentBy(Long id);
    void save(CommentEntity commentEntity);
    void update(Comment comment);
    void delete(Comment comment);
}
