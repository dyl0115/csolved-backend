package store.csolved.csolved.domain.question.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.question.Question;
import store.csolved.csolved.domain.question.dto.QuestionDto;

import java.util.List;

@Mapper
public interface QuestionMapper
{
    void insertQuestion(Question question);

    List<QuestionDto> findAllQuestions(Long offset, Long limit);
}
