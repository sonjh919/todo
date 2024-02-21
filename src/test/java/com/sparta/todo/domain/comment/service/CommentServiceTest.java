package com.sparta.todo.domain.comment.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.sparta.todo.domain.comment.dto.CommentRequestDto;
import com.sparta.todo.domain.comment.dto.CommentResponseDto;
import com.sparta.todo.domain.comment.repository.CommentRepository;
import com.sparta.todo.domain.fixture.TestComment;
import com.sparta.todo.domain.fixture.TestTodo;
import com.sparta.todo.domain.fixture.TestUser;
import com.sparta.todo.domain.todo.repository.TodoRepository;
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
public class CommentServiceTest {
    @InjectMocks
    CommentService commentService;
    @Mock
    TodoRepository todoRepository;
    @Mock
    CommentRepository commentRepository;

    private static final String CREATE_COMMENT = "댓글";
    private static final String UPDATE_COMMENT = "수정된 댓글";

    private static final Long USER_ID = 1L;
    private static final Long TODO_ID = 1L;
    private static final Long COMMENT_ID = 1L;

    private static final TestUser USER = new TestUser();
    private static final TestTodo TODO = new TestTodo(USER_ID, TODO_ID);
    private static final TestComment COMMENT = new TestComment();

    @Test
    void createComment_댓글_추가_성공() {
        //given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        ReflectionTestUtils.setField(commentRequestDto, "comment", CREATE_COMMENT);

        given(todoRepository.findTodoBy(TODO_ID)).willReturn(TODO);

        //when
        CommentResponseDto commentResponseDto = commentService.createComment(USER, TODO_ID, commentRequestDto);

        //then
        assertEquals(CREATE_COMMENT, commentResponseDto.getComment());
    }

    @Test
    void updateComment_댓글_수정_성공() {
        //given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        ReflectionTestUtils.setField(commentRequestDto, "comment", UPDATE_COMMENT);

        given(commentRepository.findCommentBy(COMMENT_ID)).willReturn(COMMENT);
        given(todoRepository.findTodoBy(TODO_ID)).willReturn(TODO);

        //when
        CommentResponseDto commentResponseDto = commentService.updateComment(USER, TODO_ID, COMMENT_ID, commentRequestDto);

        //then
        assertEquals(UPDATE_COMMENT, commentResponseDto.getComment());
    }

    @Test
    void deleteComment_댓글_삭제_성공() {
        //given

        //when
        commentService.deleteComment(TODO_ID, COMMENT_ID);

        //then
    }
}
