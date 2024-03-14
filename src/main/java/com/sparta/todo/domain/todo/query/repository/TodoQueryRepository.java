package com.sparta.todo.domain.todo.query.repository;

import static com.sparta.todo.domain.todo.model.QTodoEntity.todoEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.todo.model.TodoEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Todo findTodoBy(Long id) {
        return Todo.from(jpaQueryFactory
            .selectFrom(todoEntity)
            .where(todoEntity.todoId.eq(id)
            ).fetchFirst());
    }

    public List<TodoEntity> findByTitle(String title) {
        return jpaQueryFactory
            .selectFrom(todoEntity)
            .where(todoTitleEq(title))
            .orderBy(todoEntity.userEntity.userId.asc())
            .fetch();
    }

    private BooleanExpression todoTitleEq(String title){
        if(title==null){
            return null;
        }
        return todoEntity.title.eq(title);
    }
}
