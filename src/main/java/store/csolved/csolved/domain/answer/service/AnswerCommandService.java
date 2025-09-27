package store.csolved.csolved.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.answer.exception.AnswerDeleteDeniedException;
import store.csolved.csolved.domain.answer.exception.AnswerNotFoundException;
import store.csolved.csolved.domain.answer.exception.AnswerSaveDeniedException;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;
import store.csolved.csolved.domain.answer.mapper.AnswerMapper;
import store.csolved.csolved.domain.answer.mapper.record.AnswerDetailRecord;
import store.csolved.csolved.domain.answer.service.command.AnswerCreateCommand;

import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class AnswerCommandService
{
    private final AnswerMapper answerMapper;

    public void save(Long userId, AnswerCreateCommand command)
    {
        Answer answer = Answer.from(command);

        if (!Objects.equals(userId, answer.getAuthorId()))
        {
            throw new AnswerSaveDeniedException();
        }

        answerMapper.increaseAnswerCount(answer.getPostId());
        answerMapper.saveAnswer(answer);
    }

    public void delete(Long userId, Long answerId)
    {
        boolean commentsExist = answerMapper.existComments(answerId);
        AnswerDetailRecord answer = answerMapper.getAnswer(answerId);

        if (answer == null)
        {
            throw new AnswerNotFoundException();
        }

        if (!Objects.equals(answer.getAuthorId(), userId))
        {
            throw new AnswerDeleteDeniedException();
        }

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
