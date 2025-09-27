package store.csolved.csolved.domain.post.mapper.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.global.common.BaseEntity;
import store.csolved.csolved.domain.tag.Tag;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PostCardRecord extends BaseEntity
{
    private Long postId;
    private Long postType;
    private String title;
    private boolean anonymous;
    private Long authorId;
    private String authorNickname;
    private Long categoryId;
    private String categoryName;
    private List<Tag> tags;
    private Long views;
    private Long likes;
    private Long answerCount;
}
