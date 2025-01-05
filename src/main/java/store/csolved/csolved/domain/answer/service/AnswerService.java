package store.csolved.csolved.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.answer.Answer;
import store.csolved.csolved.domain.answer.dto.AnswerCreateForm;
import store.csolved.csolved.domain.answer.dto.AnswerDto;
import store.csolved.csolved.domain.answer.mapper.AnswerMapper;
import store.csolved.csolved.domain.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerService
{
    private final AnswerMapper answerMapper;

    @Transactional
    public void saveAnswer(User user, AnswerCreateForm form)
    {
        form.setUserId(user.getId());
        Answer answer = form.toAnswer();
        answerMapper.insertAnswer(answer);
    }

    public List<AnswerDto> provideAllAnswersByQuestionId(Long questionId)
    {
        return answerMapper.findAllAnswersByQuestionId(questionId);
    }

    @Transactional
    public void deleteAnswer(Long answerId)
    {
        boolean commentsExist = answerMapper.existCommentInAnswer(answerId);

        if (commentsExist)
        {
            answerMapper.softDeleteAnswer(answerId);
        }
        else
        {
            answerMapper.hardDeleteAnswer(answerId);
        }
    }
}
