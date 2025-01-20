package store.csolved.csolved.domain.answer.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.answer.service.dto.record.AnswerDetailRecord;
import store.csolved.csolved.domain.comment.service.dto.CommentDetailDTO;
import store.csolved.csolved.domain.comment.service.dto.CommentDetailListRecord;
import store.csolved.csolved.domain.comment.service.dto.CommentDetailRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class AnswerWithComments
{
    private Long id;
    private Long authorId;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private LocalDateTime createdAt;
    private List<CommentDetailDTO> comments;

    public static AnswerWithComments from(AnswerDetailRecord answer,
                                          List<CommentDetailRecord> comments)
    {
        return AnswerWithComments.builder()
                .id(answer.getId())
                .authorId(answer.getAuthorId())
                .authorNickname(answer.getAuthorNickname())
                .anonymous(answer.isAnonymous())
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .comments(CommentDetailDTO.from(comments))
                .build();
    }

    public static List<AnswerWithComments> from(List<AnswerDetailRecord> answers,
                                                Map<Long, CommentDetailListRecord> commentMap)
    {
        answers.stream()
                .map(
                        answer ->
                        {
                            Long answerId = answer.getId();
                            List<CommentDetailRecord> comments = commentMap.get(answerId).getComments();
                            return AnswerWithComments.from(answer, comments);
                        }
                ).toList();


    }
}