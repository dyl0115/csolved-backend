package store.csolved.csolved.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.domain.answer.mapper.AnswerMapper;
import store.csolved.csolved.domain.answer.mapper.record.AnswerWithCommentsRecord;
import store.csolved.csolved.domain.answer.service.result.AnswerWithCommentsResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerQueryService
{
    private final AnswerMapper answerMapper;

    // 게시글에 대한 답변글들, 각각의 답변글에 대한 댓글들을 모두 반환.
    public List<AnswerWithCommentsResult> getAnswersWithComments(Long postId)
    {
        List<AnswerWithCommentsRecord> records = answerMapper.getAnswersWithComments(postId);
        return AnswerWithCommentsResult.from(records);
    }
}
