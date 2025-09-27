package store.csolved.csolved.domain.answer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnswerExceptionType
{
    ANSWER_NOT_FOUND("NOTICE_NOT_FOUND", "존재하지 않는 댓글입니다."),
    SAVE_DENIED("SAVE_DENIED", "댓글을 작성할 권한이 없습니다."),
    DELETE_DENIED("DELETED_DENIED", "댓글을 삭제할 권한이 없습니다.");

    private final String code;
    private final String message;
}
