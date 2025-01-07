package store.csolved.csolved.domain.answer.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.answer.Answer;
import store.csolved.csolved.domain.answer.dto.AnswerDto;

import java.util.List;

@Mapper
public interface AnswerMapper
{
    void insertAnswer(Answer answer);

    List<AnswerDto> findAllAnswersByQuestionId(Long questionId);

    Double findAverageScoreByAnswerId(Long answerId);

    Long findVoterCountByAnswerId(Long answerId);

    boolean existUserInAnswerRatings(Long answerId, Long userId);

    void insertAnswerScore(Long answerId, Long userId, int score);

    boolean existCommentInAnswer(Long answerId);

    void softDeleteAnswer(Long answerId);

    void hardDeleteAnswerRatings(Long answerId);

    void hardDeleteAnswer(Long answerId);
}
