package store.csolved.csolved.domain.post.mapper.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.global.common.BaseEntity;
import store.csolved.csolved.domain.post.service.command.PostCreateCommand;
import store.csolved.csolved.domain.post.service.command.PostUpdateCommand;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity
{
    private int postType;
    private Long authorId;
    private boolean anonymous;
    private String title;
    private String content;
    private Long categoryId;

    public static Post from(PostCreateCommand command)
    {
        return Post.builder()
                .title(command.getTitle())
                .content(command.getContent())
                .authorId(command.getAuthorId())
                .anonymous(command.isAnonymous())
                .categoryId(command.getCategoryId())
                .build();
    }

    public static Post from(PostUpdateCommand command)
    {
        return Post.builder()
                .title(command.getTitle())
                .content(command.getContent())
                .authorId(command.getAuthorId())
                .anonymous(command.isAnonymous())
                .categoryId(command.getCategoryId())
                .build();
    }
}
