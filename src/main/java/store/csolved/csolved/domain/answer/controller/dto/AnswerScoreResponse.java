package store.csolved.csolved.domain.answer.controller.dto;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.answer.entity.Answer;

@Data
@Builder
public class AnswerScoreResponse
{
    private boolean duplicate;
    private Long prevScore;
    private Double averageScore;
    private Long voterCount;

    public static AnswerScoreResponse success(Answer answer)
    {
        Long totalScore = answer.getTotalScore();
        Long voterCount = answer.getVoterCount();

        return AnswerScoreResponse.builder()
                .duplicate(false)
                .averageScore(calculateAverageScore(totalScore, voterCount))
                .voterCount(voterCount)
                .build();
    }

    public static AnswerScoreResponse duplicate(Long prevScore)
    {
        return AnswerScoreResponse.builder()
                .duplicate(true)
                .prevScore(prevScore)
                .build();
    }

    private static Double calculateAverageScore(Long totalScore, Long voterCount)
    {
        return totalScore.doubleValue() / voterCount;
    }
}