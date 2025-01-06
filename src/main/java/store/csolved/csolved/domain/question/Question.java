package store.csolved.csolved.domain.question;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Question
{
    private Long id;

    private final Long userId;

    private final boolean isAnonymous;

    private final String title;

    private final String content;

    private final Long categoryId;

    private final Long views;

    private final Long likes;

    private final LocalDateTime createdAt;

    private final LocalDateTime deletedAt;

    public static Question create(Long questionId, Long userId, boolean isAnonymous, String title, String content, Long categoryId)
    {
        return new Question(questionId, userId, isAnonymous, title, content,
                categoryId, 0L, 0L, LocalDateTime.now(), null);
    }
}
