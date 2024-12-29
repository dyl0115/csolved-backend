package store.csolved.csolved.domain.answer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Answer
{
    private Long id;

    private Long questionId;

    private Long userId;

    private boolean isAnonymous;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;
}
