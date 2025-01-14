package store.csolved.csolved.domain.question.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.tag.dto.TagNameDTO;


import java.util.List;
import java.util.Map;

@Getter
@Builder
public class QuestionSummaryDTO
{
    private QuestionDetailDTO question;
    private List<TagNameDTO> tags;

    public static QuestionSummaryDTO from(QuestionDetailDTO question,
                                          List<TagNameDTO> tags)
    {
        return QuestionSummaryDTO.builder()
                .question(question)
                .tags(tags)
                .build();
    }

    public static List<QuestionSummaryDTO> from(List<QuestionDetailDTO> questions,
                                                Map<Long, List<TagNameDTO>> tagMap)
    {
        return questions.stream()
                .map(question -> QuestionSummaryDTO.from(question, tagMap.get(question.getId())))
                .toList();
    }
}
