package store.csolved.csolved.domain.notice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeExceptionType
{
    NOTICE_NOT_FOUND("NOTICE_NOT_FOUND", "존재하지 않는 공지사항입니다."),
    SAVE_DENIED("SAVE_DENIED", "글을 작성할 권한이 없습니다."),
    UPDATE_DENIED("UPDATE_DENIED", "글을 수정할 권한이 없습니다."),
    DELETE_DENIED("DELETED_DENIED", "글을 삭제할 권한이 없습니다."),
    ADMIN_ONLY("ADMIN_ONLY", "관리자 권한이 필요합니다.");

    private final String code;
    private final String message;
}
