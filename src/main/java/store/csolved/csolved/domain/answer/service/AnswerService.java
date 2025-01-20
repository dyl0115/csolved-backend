package store.csolved.csolved.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.entity.AnswerWithComments;
import store.csolved.csolved.domain.answer.mapper.AnswerMapper;
import store.csolved.csolved.domain.comment.mapper.CommentMapper;
import store.csolved.csolved.domain.comment.entity.AnswerCommentMap;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AnswerService
{
    private final AnswerMapper answerMapper;
    private final CommentMapper commentMapper;

    @Transactional
    public void save(Answer answer)
    {
        answerMapper.save(answer);
    }

    // 질문글에 대한 답변글들, 각각의 답변글에 대한 댓글들을 모두 반환.
    public List<AnswerWithComments> getAnswersWithComments(Long questionId)
    {
        List<Answer> answers = answerMapper.getAnswers(questionId);
        Map<Long, AnswerCommentMap> commentMap = commentMapper.getComments(extractIds(answers));
        return AnswerWithComments.from(answers, commentMap);
    }

    private List<Long> extractIds(List<Answer> answers)
    {
        return answers.stream()
                .map(Answer::getId)
                .toList();
    }

    public boolean hasAlreadyScored(Long answerId, Long userId)
    {
        return answerMapper.hasAlreadyScored(answerId, userId);
    }

    @Transactional
    public Answer score(Long answerId, Long userId, Long score)
    {
        answerMapper.score(answerId, score);
        answerMapper.saveVoter(answerId, userId, score);
        return answerMapper.getAnswer(answerId);
    }

    @Transactional
    public void delete(Long answerId)
    {
        boolean commentsExist = answerMapper.existComments(answerId);

        if (commentsExist)
        {
            answerMapper.softDelete(answerId);
        }
        else
        {
            answerMapper.hardDeleteScores(answerId);
            answerMapper.hardDelete(answerId);
        }
    }
}
