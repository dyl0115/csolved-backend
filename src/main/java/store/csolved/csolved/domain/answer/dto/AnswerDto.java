package store.csolved.csolved.domain.answer.dto;

import lombok.Data;
import store.csolved.csolved.domain.comment.dto.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AnswerDto
{
    private Long answerId;

    private Long questionId;

    private Long userId;

    private String authorNickname;

    private boolean isAnonymous;

    private String content;

    private double averageScore;

    private Long voterCount;

    private LocalDateTime createdAt;

    private List<CommentDto> comments;
}
