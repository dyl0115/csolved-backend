package store.csolved.csolved.domain.bookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.BaseEntity;
import store.csolved.csolved.common.PostType;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark extends BaseEntity
{
    private Long postId;
    private PostType postType;
    private boolean anonymous;
    private Long authorId;
    private String authorNickname;
    private Long categoryId;
    private String categoryName;
    private Long views;
    private Long likes;
    private Long answerCount;
}
