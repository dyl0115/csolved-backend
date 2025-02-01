package store.csolved.csolved.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.mapper.AnswerMapper;
import store.csolved.csolved.domain.post.mapper.PostMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerService
{
    private final AnswerMapper answerMapper;
    private final PostMapper questionMapper;

    @Transactional
    public void saveAnswer(Answer answer)
    {
        questionMapper.increaseAnswerCount(answer.getQuestionId());
        answerMapper.saveAnswer(answer);
    }

    // 질문글에 대한 답변글들, 각각의 답변글에 대한 댓글들을 모두 반환.
    public List<Answer> getAnswers(Long questionId)
    {
        return answerMapper.getAnswers(questionId);
    }

    public Long getScore(Long answerId, Long userId)
    {
        return answerMapper.getScore(answerId, userId);
    }

    @Transactional
    public Answer saveScore(Long answerId, Long userId, Long score)
    {
        answerMapper.saveScore(answerId, score);
        answerMapper.saveVoter(answerId, userId, score);
        return answerMapper.getAnswer(answerId);
    }

    @Transactional
    public Answer updateScore(Long answerId, Long userId, Long score)
    {
        Long prevScore = answerMapper.getScore(answerId, userId);
        answerMapper.updateScore(answerId, prevScore, score);
        answerMapper.updateVoter(answerId, userId, score);
        return answerMapper.getAnswer(answerId);
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
            questionMapper.decreaseAnswerCount(answer.getQuestionId());
            answerMapper.hardDeleteScores(answerId);
            answerMapper.hardDelete(answerId);
        }
    }
}
