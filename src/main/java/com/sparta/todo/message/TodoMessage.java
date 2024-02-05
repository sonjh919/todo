package com.sparta.todo.message;

public class TodoMessage {

    public TodoMessage() {
    }

    public static final String CREATE_TODO_API = "할일카드 작성 API";
    public static final String CREATE_TODO_SUCCESS = "할일카드 작성 성공";

    public static final String GET_TODO_API = "할일카드 조회 API";
    public static final String GET_TODO_SUCCESS = "할일카드 조회 성공";

    public static final String SEARCH_TODOS_API = "할일카드 전체 목록 조회 및 검색 API";
    public static final String SEARCH_TODOS_DESCRIPTION = "title 정보가 없으면 할일카드 전체의 목록을 조회합니다. "
        + "title 정보가 포함되어 있다면, 할일카드들을 제목으로 검색하여 결과를 반환합니다.";
    public static final String SEARCH_TODOS_SUCCESS = "할일카드 전체 목록 조회 및 검색 성공";

    public static final String PATCH_TODO_API = "할일카드 수정 API";
    public static final String PATCH_TODO_DESCRIPTION = "할일카드를 수정합니다. "
        + "isCompleted 정보가 포함되어 있다면 완료 여부를 체크할 수 있습니다. "
        + "ispublic 정보가 포함되어 있다면 비공개 여부를 체크할 수 있습니다.";
    public static final String PATCH_TODO_SUCCESS = "할일카드 수정 성공";

    public static final String DELETE_TODO_API = "할일카드 삭제 API";

}
