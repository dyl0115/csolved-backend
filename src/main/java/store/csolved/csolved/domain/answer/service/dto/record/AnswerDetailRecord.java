package store.csolved.csolved.domain.answer.service.dto.record;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AnswerDetailRecord
{
    private Long id;
    private Long authorId;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private LocalDateTime createdAt;
}
