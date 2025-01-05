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

    void insertAnswerScore(Long answerId, Long userId, int score);

    boolean existCommentInAnswer(Long answerId);

    void softDeleteAnswer(Long answerId);

    void hardDeleteAnswer(Long answerId);
}
