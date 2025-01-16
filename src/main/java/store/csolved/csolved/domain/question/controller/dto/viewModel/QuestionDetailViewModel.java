package store.csolved.csolved.domain.question.controller.dto.viewModel;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.service.dto.AnswerWithCommentsDTO;
import store.csolved.csolved.domain.question.service.dto.QuestionDetailDTO;
import store.csolved.csolved.domain.tag.dto.TagNameDTO;

import java.util.List;

@Getter
@Builder
public class QuestionDetailViewModel
{
    private QuestionDetailDTO question;
    private List<TagNameDTO> tags;
    private List<AnswerWithCommentsDTO> answers;

    public static QuestionDetailViewModel from(QuestionDetailDTO question,
                                               List<TagNameDTO> tags,
                                               List<AnswerWithCommentsDTO> answers)
    {
        return QuestionDetailViewModel.builder()
                .question(question)
                .tags(tags)
                .answers(answers)
                .build();
    }
}