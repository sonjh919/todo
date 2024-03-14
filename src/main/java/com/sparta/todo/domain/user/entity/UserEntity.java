package com.sparta.todo.domain.user.entity;

import com.sparta.todo.domain.todo.model.TodoEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "TB_USER")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, name = "USER_NAME")
    private String userName;

    @Column(nullable = false, unique = true, name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
    private List<TodoEntity> todoEntities = new ArrayList<>();

    public UserEntity(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}
