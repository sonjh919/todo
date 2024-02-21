package com.sparta.todo.domain.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;

import com.sparta.todo.domain.fixture.TestTodo;
import com.sparta.todo.domain.fixture.TestUser;
import com.sparta.todo.domain.todo.dto.GetTodoListResponseDto;
import com.sparta.todo.domain.todo.dto.GetTodoResponseDto;
import com.sparta.todo.domain.todo.dto.TodoRequestDto;
import com.sparta.todo.domain.todo.dto.TodoResponseDto;
import com.sparta.todo.domain.todo.repository.TodoRepository;
import com.sparta.todo.domain.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TodoServiceTest {

    @InjectMocks
    TodoService todoService;
    @Mock
    TodoRepository todoRepository;
    @Mock
    UserRepository userRepository;

    private static final String TITLE = "타이틀";
    private static final String CONTETNT = "내용";
    private static final String UPDATE_TITLE = "수정된 타이틀";
    private static final String UPDATE_CONTETNT = "수정된 내용";
    private static final Boolean IS_COMPLETED = false;
    private static final Boolean IS_PRIVATE = false;


    private static final Long TODO_ID = 1L;
    private static final Long USER_ID = 1L;

    private static final TestTodo TODO_1 = new TestTodo(USER_ID, 1L);
    private static final TestTodo TODO_2 = new TestTodo(USER_ID, 2L);
    private static final TestUser USER = new TestUser(USER_ID, List.of(TODO_1, TODO_2));

    @Test
    void createTodo_Todo_추가_성공() {
        //given
        TodoRequestDto todoRequestDto = new TodoRequestDto();
        ReflectionTestUtils.setField(todoRequestDto, "title", TITLE);
        ReflectionTestUtils.setField(todoRequestDto, "content", CONTETNT);

        //when
        TodoResponseDto todoResponseDto = todoService.createTodo(USER, todoRequestDto);

        //then
        assertEquals(TITLE, todoResponseDto.getTitle());
        assertEquals(CONTETNT, todoRequestDto.getContent());
    }

    @Test
    void getTodoById_TodoId로_조회() {
        //given
        given(todoRepository.findTodoBy(TODO_ID)).willReturn(TODO_1);

        //when
        GetTodoResponseDto getTodoResponseDto = todoService.getTodoById(TODO_ID);

        //then
        assertEquals(TODO_ID, getTodoResponseDto.getId());
    }

    @Test
    void getTodos_전체_Todo_조회_성공() {
        //given
        given(userRepository.findAll()).willReturn(List.of(USER));

        //when
        List<GetTodoListResponseDto> getTodoListResponseDtos = todoService.getTodos(TITLE);

        //then
        assertEquals(getTodoListResponseDtos.size(), 1);
        assertEquals(getTodoListResponseDtos.get(0).getTodos().size(), 2);
    }

    @Test
    void updateTodo_Todo_수정_성공() {
        //given
        TodoRequestDto todoRequestDto = new TodoRequestDto();
        ReflectionTestUtils.setField(todoRequestDto, "title", UPDATE_TITLE);
        ReflectionTestUtils.setField(todoRequestDto, "content", UPDATE_CONTETNT);

        //when
        TodoResponseDto todoResponseDto = todoService.updateTodo(USER, todoRequestDto, TODO_ID, IS_COMPLETED, IS_PRIVATE);

        //then
        assertEquals(UPDATE_TITLE, todoResponseDto.getTitle());
        assertEquals(UPDATE_CONTETNT, todoResponseDto.getContent());
        assertFalse(todoResponseDto.isCompleted());
        assertFalse(todoResponseDto.isPrivate());
    }

    @Test
    void deleteTodo_Todo_삭제_성공() {
        //given

        //when
        todoService.deleteTodo(USER, TODO_ID);

        //then
    }
}