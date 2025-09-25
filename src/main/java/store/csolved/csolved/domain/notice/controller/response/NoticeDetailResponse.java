package store.csolved.csolved.domain.notice.controller.response;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;
import store.csolved.csolved.domain.answer.mapper.record.AnswerWithCommentsRecord;
import store.csolved.csolved.domain.comment.Comment;
import store.csolved.csolved.domain.notice.mapper.entity.Notice;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class NoticeDetailResponse
{
    private Notice post;
    private List<AnswerWithCommentsRecord> answers;

    public static NoticeDetailResponse from(Notice notice,
                                            List<Answer> answers,
                                            Map<Long, List<Comment>> comments)
    {
//        return NoticeDetailResponse.builder()
//                .answers(AnswerWithCommentsRecord.from(answers, comments))
//                .post(notice)
//                .build();
        return null;
    }
}
