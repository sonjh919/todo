package com.sparta.todo.domain.comment.repository;

import com.sparta.todo.domain.comment.entity.CommentEntity;
import com.sparta.todo.domain.comment.model.Comment;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository{
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment findCommentBy(Long id) {
        return Comment.from(commentJpaRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("해당 댓글은 존재하지 않습니다.")
        ));
    }

    @Override
    public void save(CommentEntity commentEntity) {
        commentJpaRepository.save(commentEntity);
    }

    @Override
    public void update(Comment comment) {
        commentJpaRepository.saveAndFlush(comment.toEntity());
    }

    @Override
    public void delete(Comment comment) {
        commentJpaRepository.delete(comment.toEntity());
    }

}
