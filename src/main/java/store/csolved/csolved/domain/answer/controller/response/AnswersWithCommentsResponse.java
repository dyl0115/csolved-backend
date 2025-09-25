package store.csolved.csolved.domain.answer.controller.response;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.answer.mapper.record.AnswerWithCommentsRecord;
import store.csolved.csolved.domain.answer.service.result.AnswerWithCommentsResult;

import java.util.List;

@Data
@Builder
public class AnswersWithCommentsResponse
{
    List<AnswerWithCommentsResult> answers;

    public static AnswersWithCommentsResponse from(List<AnswerWithCommentsResult> results)
    {
        return AnswersWithCommentsResponse.builder()
                .answers(results)
                .build();
    }
}
