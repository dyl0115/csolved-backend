package store.csolved.csolved.domain.comment.service.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class CommentDetailListRecord
{
    private Long answerId;
    private List<CommentDetailRecord> comments;
}
