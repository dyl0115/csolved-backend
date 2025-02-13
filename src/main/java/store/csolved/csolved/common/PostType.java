package store.csolved.csolved.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostType
{
    COMMUNITY(1, "커뮤니티"),
    QUESTION(2, "면접질문"),
    CODE_REVIEW(3, "코드리뷰"),
    SUCCESS(4, "합격수기");

    private final int code;
    private final String description;
}
