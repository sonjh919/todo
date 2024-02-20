package com.sparta.todo.domain.comment.repository;

import com.sparta.todo.domain.comment.entity.CommentEntity;
import com.sparta.todo.domain.comment.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    public void save(CommentEntity commentEntity) {
        commentJpaRepository.save(commentEntity);
    }

    public void update(Comment comment) {
        commentJpaRepository.saveAndFlush(comment.toEntity());
    }

    public void delete(Comment comment) {
        commentJpaRepository.delete(comment.toEntity());
    }
}
