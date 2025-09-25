package store.csolved.csolved.domain.answer.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;
import store.csolved.csolved.domain.answer.mapper.record.AnswerWithCommentsRecord;

import java.util.List;

@Mapper
public interface AnswerMapper
{
    void saveAnswer(Answer answer);

    List<AnswerWithCommentsRecord> getAnswersWithComments(Long postId);

    Answer getAnswer(Long answerId);

    void increaseAnswerCount(Long postId);

    void decreaseAnswerCount(Long postId);

    boolean existComments(Long answerId);

    void softDelete(Long answerId);

    void hardDelete(Long answerId);
}
