package com.sparta.todo.domain.todo.model;

import com.sparta.todo.domain.todo.command.dto.TodoRequestDto;
import com.sparta.todo.domain.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TB_TODO")
@NoArgsConstructor
@AllArgsConstructor
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Column(name = "TITLE", length = 50)
    private String title;

    @Column(name = "CONTENT", length = 1024)
    private String content;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED", updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "IS_COMPLETED")
    private boolean isCompleted;

    @Column(name = "IS_PRIVATE")
    private boolean isPrivate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public TodoEntity(TodoRequestDto requestDto, UserEntity userEntity) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.isCompleted = false;
        this.isPrivate = false;
        this.userEntity = userEntity;
    }

}
