package com.sparta.todo.domain.todo.query.repository;

import static com.sparta.todo.domain.todo.model.QTodoEntity.todoEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.todo.model.TodoEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<TodoEntity> findByTitle(String title, Pageable pageable){
         List<TodoEntity> todoEntities = jpaQueryFactory
            .selectFrom(todoEntity)
            .where(todoTitleEq(title))
            .orderBy(todoEntity.todoId.asc())
             .offset(pageable.getOffset())
             .limit(pageable.getPageSize())
             .fetch();

        return new PageImpl<>(todoEntities, pageable, todoEntities.size());
    }

    private BooleanExpression todoTitleEq(String title){
        if(title==null){
            return null;
        }
        return todoEntity.title.eq(title);
    }
}
