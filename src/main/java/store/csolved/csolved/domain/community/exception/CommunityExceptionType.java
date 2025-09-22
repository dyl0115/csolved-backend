package store.csolved.csolved.domain.community.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommunityExceptionType
{
    ALREADY_LIKED("ALREADY_LIKED", "좋아요는 한번만 누를 수 있습니다.");

    private final String code;
    private final String message;
}
