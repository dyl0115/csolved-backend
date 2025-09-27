package store.csolved.csolved.domain.post.mapper.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostType
{
    COMMUNITY(1, "커뮤니티");

    private final int code;
    private final String description;
}
