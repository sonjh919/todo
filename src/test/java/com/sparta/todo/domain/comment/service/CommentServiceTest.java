package com.sparta.todo.domain.comment.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.sparta.todo.domain.comment.dto.CommentRequestDto;
import com.sparta.todo.domain.comment.dto.CommentResponseDto;
import com.sparta.todo.domain.comment.repository.CommentRepository;
import com.sparta.todo.domain.fixture.TestComment;
import com.sparta.todo.domain.fixture.TestTodo;
import com.sparta.todo.domain.fixture.TestUser;
import com.sparta.todo.domain.todo.repository.TodoRepository;
import com.sparta.todo.domain.todo.service.TodoService;
import com.sparta.todo.mock.MockCommentRepository;
import com.sparta.todo.mock.MockTodoRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CommentServiceTest {
    private CommentService commentService;
    @Mock
    TodoRepository todoRepository;

    private static final String CREATE_COMMENT = "댓글";
    private static final String UPDATE_COMMENT = "수정된 댓글";

    private static final Long USER_ID = 1L;
    private static final Long TODO_ID = 1L;
    private static final Long COMMENT_ID = 1L;

    private static final TestUser USER_1 = new TestUser(USER_ID);
    private static final TestUser USER_2 = new TestUser(USER_ID+1);
    private static final TestTodo TODO_1 = new TestTodo(USER_ID, TODO_ID);
    private static final TestTodo TODO_2 = new TestTodo(USER_ID, TODO_ID+1);

    @BeforeEach
    void init(){
        CommentRepository commentRepository = new MockCommentRepository();
        commentService = new CommentService(todoRepository, commentRepository);
    }

    @Nested
    class createComment_댓글_추가_테스트 {
        @Test
        void 댓글_추가_성공() {
            //given
            CommentRequestDto commentRequestDto = new CommentRequestDto();
            ReflectionTestUtils.setField(commentRequestDto, "comment", CREATE_COMMENT);

            given(todoRepository.findTodoBy(TODO_ID)).willReturn(TODO_1);

            //when
            CommentResponseDto commentResponseDto = commentService.createComment(USER_1, TODO_ID,
                commentRequestDto);

            //then
            assertEquals(COMMENT_ID, commentResponseDto.getCommentId());
            assertEquals(CREATE_COMMENT, commentResponseDto.getComment());
        }

    }

    @Nested
    class updateComment_댓글_수정_테스트 {
        @Test
        void 댓글_수정_성공() {
            //given
            CommentRequestDto commentRequestDto = new CommentRequestDto();
            ReflectionTestUtils.setField(commentRequestDto, "comment", CREATE_COMMENT);

            given(todoRepository.findTodoBy(TODO_ID)).willReturn(TODO_1);
            commentService.createComment(USER_1, TODO_ID, commentRequestDto);

            ReflectionTestUtils.setField(commentRequestDto, "comment", UPDATE_COMMENT);

            //when
            CommentResponseDto commentResponseDto = commentService.updateComment(USER_1, TODO_ID,
                COMMENT_ID, commentRequestDto);

            //then
            assertEquals(UPDATE_COMMENT, commentResponseDto.getComment());
        }

        @Test
        void 작성자가_아닐_경우_댓글_수정_실패() {
            //given
            CommentRequestDto commentRequestDto = new CommentRequestDto();
            ReflectionTestUtils.setField(commentRequestDto, "comment", CREATE_COMMENT);

            given(todoRepository.findTodoBy(TODO_ID)).willReturn(TODO_1);
            commentService.createComment(USER_1, TODO_ID, commentRequestDto);

            ReflectionTestUtils.setField(commentRequestDto, "comment", UPDATE_COMMENT);

            //when-then
            assertThrows(AccessDeniedException.class,
                () -> commentService.updateComment(USER_2, TODO_ID, COMMENT_ID, commentRequestDto));
        }
        @Test
        void Todo에_해당_댓글이_존재하지_않을_경우_댓글_수정_실패() {
            //given
            CommentRequestDto commentRequestDto = new CommentRequestDto();
            ReflectionTestUtils.setField(commentRequestDto, "comment", CREATE_COMMENT);

            given(todoRepository.findTodoBy(TODO_ID)).willReturn(TODO_1);
            commentService.createComment(USER_1, TODO_ID, commentRequestDto);

            given(todoRepository.findTodoBy(TODO_ID+1)).willReturn(TODO_2);
            ReflectionTestUtils.setField(commentRequestDto, "comment", UPDATE_COMMENT);

            //when-then
            assertThrows(NoSuchElementException.class,
                () -> commentService.updateComment(USER_1, TODO_ID+1, COMMENT_ID, commentRequestDto));
        }
    }

    @Nested
    class deleteComment_댓글_삭제_테스트 {
        @Test
        void 댓글_삭제_성공() {
            //given
            CommentRequestDto commentRequestDto = new CommentRequestDto();
            ReflectionTestUtils.setField(commentRequestDto, "comment", CREATE_COMMENT);

            given(todoRepository.findTodoBy(TODO_ID)).willReturn(TODO_1);
            commentService.createComment(USER_1, TODO_ID, commentRequestDto);

            //when
            commentService.deleteComment(USER_1, TODO_ID, COMMENT_ID);

            //then
        }

        @Test
        void 작성자가_아닐_경우_댓글_삭제_실패(){
            //given
            CommentRequestDto commentRequestDto = new CommentRequestDto();
            ReflectionTestUtils.setField(commentRequestDto, "comment", CREATE_COMMENT);

            given(todoRepository.findTodoBy(TODO_ID)).willReturn(TODO_1);
            commentService.createComment(USER_1, TODO_ID, commentRequestDto);

            //when-then
            assertThrows(AccessDeniedException.class,
                () -> commentService.deleteComment(USER_2, TODO_ID, COMMENT_ID));
        }

        @Test
        void Todo에_해당_댓글이_존재하지_않을_경우_댓글_삭제_실패(){
            //given
            CommentRequestDto commentRequestDto = new CommentRequestDto();
            ReflectionTestUtils.setField(commentRequestDto, "comment", CREATE_COMMENT);

            given(todoRepository.findTodoBy(TODO_ID)).willReturn(TODO_1);
            commentService.createComment(USER_1, TODO_ID, commentRequestDto);

            given(todoRepository.findTodoBy(TODO_ID+1)).willReturn(TODO_2);
            ReflectionTestUtils.setField(commentRequestDto, "comment", UPDATE_COMMENT);

            //when-then
            assertThrows(NoSuchElementException.class,
                () -> commentService.deleteComment(USER_1, TODO_ID+1, COMMENT_ID));
        }
    }
}
