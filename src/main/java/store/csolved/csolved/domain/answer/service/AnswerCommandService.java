package store.csolved.csolved.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.global.exception.CsolvedException;
import store.csolved.csolved.global.exception.ExceptionCode;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;
import store.csolved.csolved.domain.answer.mapper.AnswerMapper;
import store.csolved.csolved.domain.answer.mapper.record.AnswerDetailRecord;
import store.csolved.csolved.domain.answer.service.command.AnswerCreateCommand;
import store.csolved.csolved.domain.post.mapper.PostMapper;

import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class AnswerCommandService
{
    private final AnswerMapper answerMapper;
    private final PostMapper postMapper;

    public void save(Long userId, AnswerCreateCommand command)
    {
        Answer answer = Answer.from(command);

        Boolean postExist = postMapper.isExist(answer.getPostId());

        if (!postExist)
        {
            throw new CsolvedException(ExceptionCode.POST_NOT_FOUND);
        }

        if (!Objects.equals(userId, answer.getAuthorId()))
        {
            throw new CsolvedException(ExceptionCode.ANSWER_SAVE_DENIED);
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
            throw new CsolvedException(ExceptionCode.ANSWER_NOT_FOUND);
        }

        if (!Objects.equals(answer.getAuthorId(), userId))
        {
            throw new CsolvedException(ExceptionCode.ANSWER_DELETE_DENIED);
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
