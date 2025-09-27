package store.csolved.csolved.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode
{
    //게시글
    POST_NOT_FOUND("POST_NOT_FOUND", "존재하지 않는 게시글입니다.", HttpStatus.NOT_FOUND),

    //답변
    ANSWER_NOT_FOUND("ANSWER_NOT_FOUND", "존재하지 않는 답변입니다.", HttpStatus.NOT_FOUND),
    SAVE_DENIED("SAVE_DENIED", "답변을 작성할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    DELETE_DENIED("DELETED_DENIED", "답변을 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN),

    //북마크
    ALREADY_BOOKMARKED("ALREADY_BOOKMARKED", "이미 북마크한 게시글입니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
