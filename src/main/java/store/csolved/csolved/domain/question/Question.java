package store.csolved.csolved.domain.question;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

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

    public static Question create(Long userId, String title, String content, Long categoryId)
    {
        return new Question(null, userId, true, title, content,
                categoryId, 0L, 0L, LocalDateTime.now(), null);
    }

    @Override
    public String toString()
    {
        return "Question{" +
                "id=" + id +
                ", userId=" + userId +
                ", isAnonymous=" + isAnonymous +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", categoryId=" + categoryId +
                ", views=" + views +
                ", likes=" + likes +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
