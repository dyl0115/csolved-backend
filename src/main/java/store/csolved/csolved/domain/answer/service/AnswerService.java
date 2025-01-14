package store.csolved.csolved.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.answer.Answer;
import store.csolved.csolved.domain.answer.controller.dto.AnswerCreateForm;
import store.csolved.csolved.domain.answer.service.dto.AnswerWithCommentsDTO;
import store.csolved.csolved.domain.answer.mapper.AnswerMapper;
import store.csolved.csolved.domain.answer.service.dto.AnswerDetailRecord;
import store.csolved.csolved.domain.comment.mapper.CommentMapper;
import store.csolved.csolved.domain.comment.service.dto.CommentDetailListRecord;
import store.csolved.csolved.domain.user.User;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AnswerService
{
    private final AnswerMapper answerMapper;
    private final CommentMapper commentMapper;

    @Transactional
    public void saveAnswer(User user, AnswerCreateForm form)
    {
        form.setUserId(user.getId());
        Answer answer = form.toAnswer();
        answerMapper.insertAnswer(answer);
    }

    // 질문글에 대한 답변글들, 각각의 답변글에 대한 댓글들을 모두 반환.
    public List<AnswerWithCommentsDTO> getAnswersWithComments(Long questionId)
    {
        List<AnswerDetailRecord> answers = answerMapper.getAnswersByQuestionId(questionId);
        Map<Long, CommentDetailListRecord> comments = commentMapper.getCommentsByAnswerIds(answers.stream().map(AnswerDetailRecord::getId).toList());
        return AnswerWithCommentsDTO.from(answers, comments);
    }

    public boolean hasAlreadyRated(Long answerId, Long userId)
    {
        return answerMapper.existUserInAnswerRatings(answerId, userId);
    }

    public Double findAverageScore(Long answerId)
    {
        return answerMapper.findAverageScoreByAnswerId(answerId);
    }

    public Long findVoterCount(Long answerId)
    {
        return answerMapper.findVoterCountByAnswerId(answerId);
    }

    @Transactional
    public void rateAnswer(Long answerId, Long userId, Integer score)
    {
        answerMapper.insertAnswerScore(answerId, userId, score);
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
            answerMapper.hardDeleteAnswerRatings(answerId);
            answerMapper.hardDeleteAnswer(answerId);
        }
    }
}
