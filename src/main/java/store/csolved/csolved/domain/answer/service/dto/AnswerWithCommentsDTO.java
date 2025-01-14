package store.csolved.csolved.domain.answer.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.comment.service.dto.CommentDetailDTO;
import store.csolved.csolved.domain.comment.service.dto.CommentDetailListRecord;
import store.csolved.csolved.domain.comment.service.dto.CommentDetailRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class AnswerWithCommentsDTO
{
    private Long id;
    private boolean anonymous;
    private String authorNickname;
    private String content;
    private LocalDateTime createdAt;
    private List<CommentDetailDTO> comments;

    public static AnswerWithCommentsDTO from(AnswerDetailRecord answer,
                                             List<CommentDetailRecord> comments)
    {
        return AnswerWithCommentsDTO.builder()
                .id(answer.getId())
                .anonymous(answer.isAnonymous())
                .authorNickname(answer.getAuthorNickname())
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .comments(CommentDetailDTO.from(comments))
                .build();
    }

    public static List<AnswerWithCommentsDTO> from(List<AnswerDetailRecord> answers,
                                                   Map<Long, CommentDetailListRecord> commentMap)
    {
        return answers.stream()
                .map(
                        answer ->
                        {
                            Long answerId = answer.getId();
                            List<CommentDetailRecord> comments = commentMap.get(answerId).getComments();
                            return AnswerWithCommentsDTO.from(answer, comments);
                        }
                ).toList();
    }
}