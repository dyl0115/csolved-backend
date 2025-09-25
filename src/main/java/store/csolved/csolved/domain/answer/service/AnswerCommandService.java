package store.csolved.csolved.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;
import store.csolved.csolved.domain.answer.mapper.AnswerMapper;
import store.csolved.csolved.domain.answer.mapper.record.AnswerWithCommentsRecord;
import store.csolved.csolved.domain.answer.service.command.AnswerCreateCommand;
import store.csolved.csolved.domain.answer.service.result.AnswerWithCommentsResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerCommandService
{
    private final AnswerMapper answerMapper;

    @Transactional
    public void save(AnswerCreateCommand command)
    {
        Answer answer = Answer.from(command);
        answerMapper.increaseAnswerCount(answer.getPostId());
        answerMapper.saveAnswer(answer);
    }

    @Transactional
    public void delete(Long answerId)
    {
        boolean commentsExist = answerMapper.existComments(answerId);
        Answer answer = answerMapper.getAnswer(answerId);

        if (commentsExist)
        {
            answerMapper.softDelete(answerId);
        }
        else
        {
            answerMapper.decreaseAnswerCount(answer.getPostId());
            answerMapper.hardDelete(answerId);
        }
    }
}
