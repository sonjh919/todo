package com.sparta.todo.domain.todo.command.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.sparta.todo.domain.fixture.TestTodo;
import com.sparta.todo.domain.fixture.TestUser;
import com.sparta.todo.domain.todo.command.TodoService;
import com.sparta.todo.domain.todo.query.service.TodoQueryServiceImpl;
import com.sparta.todo.domain.todo.query.dto.GetTodoResponseDto;
import com.sparta.todo.domain.todo.command.dto.TodoRequestDto;
import com.sparta.todo.domain.todo.command.dto.TodoResponseDto;
import com.sparta.todo.domain.todo.command.repository.TodoRepository;
import com.sparta.todo.mock.MockTodoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TodoServiceTest {
    private TodoService todoService;

    private static final String TITLE_1 = "타이틀 1";
    private static final String TITLE_2 = "타이틀 2";
    private static final String CONTETNT = "내용";
    private static final String UPDATE_TITLE = "수정된 타이틀";
    private static final String UPDATE_CONTETNT = "수정된 내용";
    private static final Boolean IS_COMPLETED = false;
    private static final Boolean IS_PRIVATE = false;

    private static final Long TODO_ID = 1L;
    private static final Long USER_ID = 1L;

    private static final TestTodo TODO_1 = new TestTodo(USER_ID, TODO_ID,  TITLE_1);
    private static final TestTodo TODO_2 = new TestTodo(USER_ID, TODO_ID+1, TITLE_2);
    private static final TestUser USER = new TestUser(USER_ID, List.of(TODO_1, TODO_2));

    @BeforeEach
    void init(){
        TodoRepository todoRepository = new MockTodoRepository();
        todoService = new TodoService(todoRepository);
    }

    @Nested
    class createTodo_Todo_추가_테스트 {
        @Test
        void Todo_추가_성공() {
            //given
            TodoRequestDto todoRequestDto = new TodoRequestDto();
            setDto(todoRequestDto, TITLE_1, CONTETNT);

            //when
            TodoResponseDto todoResponseDto = todoService.createTodo(USER, todoRequestDto);

            //then
            assertEquals(TODO_ID, todoResponseDto.getTodoId());
            assertEquals(TITLE_1, todoResponseDto.getTitle());
            assertEquals(CONTETNT, todoRequestDto.getContent());
        }
    }

    @Nested
    class updateTodo_Todo_수정_테스트 {
        @Test
        void Todo_수정_성공() {
            //given
            TodoRequestDto todoRequestDto = new TodoRequestDto();
            setDto(todoRequestDto, TITLE_1, CONTETNT);
            todoService.createTodo(USER, todoRequestDto);

            TodoRequestDto updatetodoRequestDto = new TodoRequestDto();
            setDto(updatetodoRequestDto, UPDATE_TITLE, UPDATE_CONTETNT);

            //when
            TodoResponseDto todoResponseDto = todoService.updateTodo(USER, updatetodoRequestDto,
                TODO_ID, IS_COMPLETED, IS_PRIVATE);

            //then
            assertEquals(UPDATE_TITLE, todoResponseDto.getTitle());
            assertEquals(UPDATE_CONTETNT, todoResponseDto.getContent());
            assertFalse(todoResponseDto.isCompleted());
            assertFalse(todoResponseDto.isPrivate());
        }

        @Test
        void 작성자가_아닐_경우_수정_실패() {
            //given
            TodoRequestDto todoRequestDto = new TodoRequestDto();
            setDto(todoRequestDto, UPDATE_TITLE, UPDATE_CONTETNT);
            todoService.createTodo(USER, todoRequestDto);

            //when-then
            assertThrows(NoSuchElementException.class,
                () -> todoService.updateTodo(USER, todoRequestDto, TODO_ID + 2, IS_COMPLETED,
                    IS_PRIVATE));
        }
    }

    @Nested
    class deleteTodo_Todo_삭제_테스트 {
        @Test
        void Todo_삭제_성공() {
            //given
            TodoRequestDto todoRequestDto = new TodoRequestDto();
            setDto(todoRequestDto, TITLE_1, CONTETNT);
            todoService.createTodo(USER, todoRequestDto);

            //when
            todoService.deleteTodo(USER, TODO_ID);

            //then
        }

        @Test
        void 작성자가_아닐_경우_삭제_실패() {
            //given
            TodoRequestDto todoRequestDto = new TodoRequestDto();
            setDto(todoRequestDto, UPDATE_TITLE, UPDATE_CONTETNT);
            todoService.createTodo(USER, todoRequestDto);

            //when-then
            assertThrows(NoSuchElementException.class,
                () -> todoService.deleteTodo(USER, TODO_ID + 2));
        }
    }

//    @Nested
//    class getTodoById_Todo_조회_테스트 {
//        @Test
//        void TodoId로_조회_성공() {
//            //given
//            TodoRequestDto todoRequestDto = new TodoRequestDto();
//            setDto(todoRequestDto, TITLE_1, CONTETNT);
//
//            todoService.createTodo(USER, todoRequestDto);
//
//            //when
//            GetTodoResponseDto getTodoResponseDto = todoService.getTodoById(TODO_ID);
//
//            //then
//            assertEquals(TODO_ID, getTodoResponseDto.getId());
//        }
//
//        @Test
//        void ID가_존재하지_않을_경우_조회_실패() {
//            //given
//            //when-then
//            assertThrows(EntityNotFoundException.class, () -> todoService.getTodoById(TODO_ID));
//        }
//    }
//
//    @Nested
//    class getTodos_Todo_전체_조회_테스트 {
//        @Test
//        void Todo_검색_조회_성공() {
//            //given
//            TodoRequestDto todoRequestDto = new TodoRequestDto();
//            setDto(todoRequestDto, TITLE_1, CONTETNT);
//
//            given(userRepository.findAll()).willReturn(
//                List.of(USER)); // Todo가 USER에 너무 의존적 -> 해결 필요
//
//            todoService.createTodo(USER, todoRequestDto);
//
//            //when
//            List<GetTodoListResponseDto> getTodoListResponseDtos = todoService.getTodos(TITLE_1);
//
//            //then
//            assertEquals(getTodoListResponseDtos.size(), 1);
//            assertEquals(getTodoListResponseDtos.get(0).getTodos().size(), 1);
//        }
//
//        @Test
//        void 전체_Todo_조회_성공() {
//            //given
//            TodoRequestDto todoRequestDto = new TodoRequestDto();
//            setDto(todoRequestDto, TITLE_1, CONTETNT);
//
//            given(userRepository.findAll()).willReturn(
//                List.of(USER)); // Todo가 USER에 너무 의존적 -> 해결 필요
//
//            todoService.createTodo(USER, todoRequestDto);
//
//            //when
//            List<GetTodoListResponseDto> getTodoListResponseDtos = todoService.getTodos(null);
//
//            //then
//            assertEquals(getTodoListResponseDtos.size(), 1);
//            assertEquals(getTodoListResponseDtos.get(0).getTodos().size(), 2);
//        }
//    }

    private <T> void setDto(T dto, String title, String content) {
        ReflectionTestUtils.setField(dto, "title", title);
        ReflectionTestUtils.setField(dto, "content", content);
    }

}