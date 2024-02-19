package com.sparta.todo.domain.comment.entity;

import com.sparta.todo.domain.comment.dto.CommentRequestDto;
import com.sparta.todo.domain.todo.entity.Todo;
import com.sparta.todo.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@Table(name = "TB_COMMENT")
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Todo todo;

    @Column(name = "COMMENT", length = 512)
    private String comment;

    public Comment(CommentRequestDto requestDto, Todo todo, User user) {
        this.comment = requestDto.getComment();
        this.user = user;
        this.todo = todo;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
