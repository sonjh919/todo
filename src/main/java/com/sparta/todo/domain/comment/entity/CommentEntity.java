package com.sparta.todo.domain.comment.entity;

import com.sparta.todo.domain.comment.dto.CommentRequestDto;
import com.sparta.todo.domain.todo.model.TodoEntity;
import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.domain.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Table(name = "TB_COMMENT")
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TodoEntity todoEntity;

    @Column(name = "COMMENT", length = 512)
    private String comment;

    public CommentEntity(CommentRequestDto requestDto, Todo todo, User user) {
        this.comment = requestDto.getComment();
        this.userEntity = user.toEntity();
        this.todoEntity = todo.toEntity();
    }
}
