package store.csolved.csolved.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.BaseEntity;
import store.csolved.csolved.domain.post.service.command.CommunityCreateCommand;
import store.csolved.csolved.domain.post.service.command.CommunityUpdateCommand;
import store.csolved.csolved.domain.tag.Tag;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity
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

    public static Post from(CommunityCreateCommand command)
    {
        return Post.builder()
                .title(command.getTitle())
                .content(command.getContent())
                .authorId(command.getAuthorId())
                .anonymous(command.isAnonymous())
                .views(0L)
                .likes(0L)
                .answerCount(0L)
                .categoryId(command.getCategoryId())
                .tags(command.getTags())
                .build();
    }

    public static Post from(CommunityUpdateCommand command)
    {
        return Post.builder()
                .title(command.getTitle())
                .content(command.getContent())
                .authorId(command.getAuthorId())
                .anonymous(command.isAnonymous())
                .views(0L)
                .likes(0L)
                .answerCount(0L)
                .categoryId(command.getCategoryId())
                .tags(command.getTags())
                .build();
    }
}
