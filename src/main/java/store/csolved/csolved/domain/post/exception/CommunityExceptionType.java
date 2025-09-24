package store.csolved.csolved.domain.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommunityExceptionType
{
    ALREADY_LIKED("ALREADY_LIKED", "좋아요는 한번만 누를 수 있습니다."),
    POST_NOT_FOUND("POST_NOT_FOUND", "존재하지 않는 게시글입니다."),
    UPDATE_DENIED("UPDATE_DENIED", "글을 수정할 권한이 없습니다."),
    DELETE_DENIED("DELETE_DENIED", "글을 삭제할 권한이 없습니다.");

    private final String code;
    private final String message;
}
