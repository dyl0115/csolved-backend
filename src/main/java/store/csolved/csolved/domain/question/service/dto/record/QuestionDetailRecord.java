package store.csolved.csolved.domain.question.service.dto.record;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestionDetailRecord
{
    private Long id;
    private String title;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private Long categoryId;
    private String categoryName;
    private Long views;
    private Long likes;
    private Long answerCount;
    private LocalDateTime createdAt;
}
