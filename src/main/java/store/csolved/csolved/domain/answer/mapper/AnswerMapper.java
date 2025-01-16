package store.csolved.csolved.domain.answer.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.answer.Answer;
import store.csolved.csolved.domain.answer.service.dto.record.AnswerDetailRecord;

import java.util.List;

@Mapper
public interface AnswerMapper
{
    void insertAnswer(Answer answer);

    List<AnswerDetailRecord> getAnswersByQuestionId(Long questionId);

    Double findAverageScoreByAnswerId(Long answerId);

    Long findVoterCountByAnswerId(Long answerId);

    boolean existUserInAnswerRatings(Long answerId, Long userId);

    void insertAnswerScore(Long answerId, Long userId, int score);

    boolean existCommentInAnswer(Long answerId);

    void softDeleteAnswer(Long answerId);

    void hardDeleteAnswerRatings(Long answerId);

    void hardDeleteAnswer(Long answerId);
}
