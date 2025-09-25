package store.csolved.csolved.domain.answer.mapper.record;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import store.csolved.csolved.domain.answer.mapper.entity.Answer;
import store.csolved.csolved.domain.comment.Comment;
import store.csolved.csolved.domain.comment.mapper.record.CommentDetailRecord;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerWithCommentsRecord
{
    private Long id;
    private Long authorId;
    private String authorProfileImage;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private LocalDateTime createdAt;
    private List<CommentDetailRecord> comments;

    @Override
    public String toString()
    {
        return "AnswerWithCommentsRecord{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", authorProfileImage='" + authorProfileImage + '\'' +
                ", authorNickname='" + authorNickname + '\'' +
                ", anonymous=" + anonymous +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", comments=" + comments +
                '}';
    }
}