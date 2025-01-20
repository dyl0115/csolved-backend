package store.csolved.csolved.domain.answer.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.answer.entity.Answer;

import java.util.List;

@Mapper
public interface AnswerMapper
{
    Long save(Answer answer);

    List<Answer> getAnswers(Long questionId);

    Answer getAnswer(Long answerId);

    boolean hasAlreadyScored(Long answerId, Long userId);

    void score(Long answerId, Long score);

    void saveVoter(Long answerId, Long userId, Long score);

    boolean existComments(Long answerId);

    void hardDeleteScores(Long answerId);

    void softDelete(Long answerId);

    void hardDelete(Long answerId);
}
