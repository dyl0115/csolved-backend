package store.csolved.csolved.domain.question.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Question
{
    private final Long id;
    private final String title;
    private final String content;
    private final Long authorId;
    private final boolean anonymous;
    private final Long categoryId;
    private final Long views;
    private final Long likes;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    public static Question create(
            Long id,
            String title,
            String content,
            Long authorId,
            boolean anonymous,
            Long categoryId)
    {
        return new Question(
                id,
                title,
                content,
                authorId,
                anonymous,
                categoryId,
                0L,
                0L,
                LocalDateTime.now(),
                null);
    }
}
