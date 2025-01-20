package store.csolved.csolved.domain.answer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class Answer
{
    private final Long id;
    private final Long questionId;
    private final Long userId;
    private final boolean isAnonymous;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    public static Answer create(Long questionId,
                                Long userId,
                                Boolean isAnonymous,
                                String content)
    {
        return new Answer(null,
                questionId,
                userId,
                isAnonymous,
                content,
                LocalDateTime.now(),
                null);
    }
}
