package store.csolved.csolved.domain.comment.mapper.record;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDetailRecord
{
    private Long id;
    private Long answerId;
    private Long authorId;
    private String authorProfileImage;
    private String authorNickname;
    private boolean anonymous;
    private String content;
    private LocalDateTime createdAt;

    @Override
    public String toString()
    {
        return "CommentDetailRecord{" +
                "id=" + id +
                ", answerId=" + answerId +
                ", authorId=" + authorId +
                ", authorProfileImage='" + authorProfileImage + '\'' +
                ", authorNickname='" + authorNickname + '\'' +
                ", anonymous=" + anonymous +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
