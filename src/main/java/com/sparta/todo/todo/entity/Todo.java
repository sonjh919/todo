package com.sparta.todo.todo.entity;

import com.sparta.todo.todo.dto.TodoRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@Table(name = "TB_TODO")
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TodoId;

    @Column(name = "TITLE", length = 50)
    private String title;

    @Column(name = "CONTENT", length = 1024)
    private String content;

    @Column(name = "AUTHOR")
    private String author;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED", updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "IS_COMPLETED")
    private boolean isCompleted;

    @Column(name = "IS_PRIVATE")
    private boolean isPrivate;

    public Todo(TodoRequestDto requestDto, String author) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.author = author;
        this.isCompleted = false;
        this.isPrivate = false;
    }

}
