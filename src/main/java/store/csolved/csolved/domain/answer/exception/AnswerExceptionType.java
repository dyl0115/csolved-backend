package store.csolved.csolved.domain.answer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnswerExceptionType
{
    POST_NOT_FOUND("POST_NOT_FOUND", "게시글이 삭제되었습니다."),
    ANSWER_NOT_FOUND("ANSWER_NOT_FOUND", "존재하지 않는 답변입니다."),
    SAVE_DENIED("SAVE_DENIED", "답변을 작성할 권한이 없습니다."),
    DELETE_DENIED("DELETED_DENIED", "답변을 삭제할 권한이 없습니다.");

    private final String code;
    private final String message;
}
