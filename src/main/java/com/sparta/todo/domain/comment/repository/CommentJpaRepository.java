package com.sparta.todo.domain.comment.repository;

import com.sparta.todo.domain.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

}
