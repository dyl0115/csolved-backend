package store.csolved.csolved.domain.question.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.service.dto.AnswerWithCommentsDTO;
import store.csolved.csolved.domain.question.service.dto.QuestionDetailDTO;
import store.csolved.csolved.domain.tag.dto.TagNameDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class QuestionDetailViewModel
{
    private Long id;
    private String title;
    private boolean anonymous;
    private String authorNickname;
    private String content;
    private String categoryName;
    private Long views;
    private Long likes;
    private LocalDateTime createdAt;
    private List<TagNameDTO> tags;
    private List<AnswerWithCommentsDTO> answers;

    public static QuestionDetailViewModel from(QuestionDetailDTO question,
                                               List<TagNameDTO> tags,
                                               List<AnswerWithCommentsDTO> answers)
    {
        return QuestionDetailViewModel.builder()
                .id(question.getId())
                .title(question.getTitle())
                .anonymous(question.isAnonymous())
                .authorNickname(question.getAuthorNickname())
                .content(question.getContent())
                .categoryName(question.getCategoryName())
                .views(question.getViews())
                .likes(question.getLikes())
                .createdAt(question.getCreatedAt())
                .tags(tags)
                .answers(answers)
                .build();
    }
}