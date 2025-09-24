package store.csolved.csolved.domain.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostType
{
    NOTICE(0, "공지사항"),
    COMMUNITY(1, "커뮤니티");

    private final int code;
    private final String description;
}
