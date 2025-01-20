package store.csolved.csolved.domain.answer.controller.dto;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.answer.entity.Answer;

@Data
@Builder
public class AnswerScoreResponse
{
    private Double averageScore;
    private Long voterCount;

    public static AnswerScoreResponse from(Answer answer)
    {
        Long totalScore = answer.getTotalScore();
        Long voterCount = answer.getVoterCount();

        return AnswerScoreResponse.builder()
                .averageScore(calculateAverageScore(totalScore, voterCount))
                .voterCount(voterCount)
                .build();
    }

    private static Double calculateAverageScore(Long totalScore, Long voterCount)
    {
        return totalScore.doubleValue() / voterCount;
    }
}