package store.csolved.csolved.domain.community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.Post;
import store.csolved.csolved.domain.community.service.command.CommunityCreateCommand;
import store.csolved.csolved.domain.community.service.command.CommunityUpdateCommand;
import store.csolved.csolved.domain.tag.Tag;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Community extends Post
{
    private Long categoryId;
    private String categoryName;
    private List<Tag> tags;

    public static Community from(CommunityCreateCommand command)
    {
        return Community.builder()
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

    public static Community from(CommunityUpdateCommand command)
    {
        return Community.builder()
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
