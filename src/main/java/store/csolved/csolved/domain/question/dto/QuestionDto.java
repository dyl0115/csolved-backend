package store.csolved.csolved.domain.question.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionDto
{
    private Long questionId;

    private Long authorId;

    private String authorNickname;

    private boolean anonymous;

    private String title;

    private String content;

    private Long answerCount;

    private Long categoryId;

    private String categoryName;

    private Long views;

    private Long likes;

    private LocalDateTime createdAt;

    private List<String> tags;
}
