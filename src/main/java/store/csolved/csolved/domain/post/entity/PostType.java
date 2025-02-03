package store.csolved.csolved.domain.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PostType
{
    COMMUNITY(1, "커뮤니티"),
    QUESTION(2, "면접질문"),
    CODE(3, "코드리뷰"),
    SUCCESS(4, "합격수기");

    private final int code;
    private final String description;

    public static PostType of(int code)
    {
        return Arrays.stream(PostType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 PostType 코드입니다. code: " + code));
    }
}
