package store.csolved.csolved.domain.question;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import store.csolved.csolved.domain.category.Category;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Question
{
    private Long id;

    private final Long userId;

    private final String title;

    private final String content;

    private final Long categoryId;

    private final Integer views;

    private final LocalDateTime createdAt;

    private final LocalDateTime deletedAt;

    public static Question create(Long userId, String title, String content, Long categoryId)
    {
        return new Question(null, userId, title, content,
                categoryId, 0, LocalDateTime.now(), null);
    }
}
