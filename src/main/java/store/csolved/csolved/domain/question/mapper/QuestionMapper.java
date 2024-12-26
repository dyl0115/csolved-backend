package store.csolved.csolved.domain.question.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.question.Question;

@Mapper
public interface QuestionMapper
{
    public void insertQuestion(Question question);

}
