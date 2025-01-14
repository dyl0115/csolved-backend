package store.csolved.csolved.domain.question.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.question.service.dto.record.QuestionDetailRecord;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class QuestionDetailDTO
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

    public static QuestionDetailDTO from(QuestionDetailRecord record)
    {
        return QuestionDetailDTO.builder()
                .id(record.getId())
                .title(record.getTitle())
                .authorNickname(record.getAuthorNickname())
                .anonymous(record.isAnonymous())
                .content(record.getContent())
                .categoryId(record.getCategoryId())
                .categoryName(record.getCategoryName())
                .views(record.getViews())
                .likes(record.getLikes())
                .createdAt(record.getCreatedAt())
                .build();
    }

    public static List<QuestionDetailDTO> from(List<QuestionDetailRecord> records)
    {
        return records.stream()
                .map(QuestionDetailDTO::from)
                .toList();
    }
}
