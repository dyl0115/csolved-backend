package store.csolved.csolved.domain.comment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class Comment
{
    private final Long id;

    private final Long answerId;

    private final Long userId;

    private final boolean isAnonymous;

    private final String content;

    private final LocalDateTime createdAt;

    private final LocalDateTime deletedAt;

    public static Comment create(Long answerId,
                                 Long userId,
                                 boolean isAnonymous,
                                 String content)
    {
        return new Comment(null,
                answerId,
                userId,
                isAnonymous,
                content,
                LocalDateTime.now(),
                null);
    }

}
