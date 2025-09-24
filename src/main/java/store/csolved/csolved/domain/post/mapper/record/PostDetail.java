package store.csolved.csolved.domain.post.mapper.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.BaseEntity;
import store.csolved.csolved.domain.post.mapper.entity.Post;
import store.csolved.csolved.domain.post.service.command.PostCreateCommand;
import store.csolved.csolved.domain.post.service.command.PostUpdateCommand;
import store.csolved.csolved.domain.tag.Tag;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetail extends BaseEntity
{
    private String postType;
    private String title;
    private boolean anonymous;
    private Long authorId;
    private String authorNickname;
    private String content;
    private Long views;
    private Long likes;
    private Long answerCount;
    private Long categoryId;
    private String categoryName;
    private List<Tag> tags;
    
}
