package store.csolved.csolved.domain.answer.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.answer.entity.Answer;

import java.util.List;

@Mapper
public interface AnswerMapper
{
    Long saveAnswer(Answer answer);

    List<Answer> getAnswers(Long questionId);

    Answer getAnswer(Long answerId);

    Long getScore(Long answerId, Long userId);

    void saveScore(Long answerId, Long score);

    void updateScore(Long answerId, Long oldScore, Long newScore);

    void updateVoter(Long answerId, Long userId, Long score);

    void saveVoter(Long answerId, Long userId, Long score);

    boolean existComments(Long answerId);

    void hardDeleteScores(Long answerId);

    void softDelete(Long answerId);

    void hardDelete(Long answerId);
}
