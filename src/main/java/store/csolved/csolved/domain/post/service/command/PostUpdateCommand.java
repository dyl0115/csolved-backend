package store.csolved.csolved.domain.post.service.command;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.post.controller.request.PostUpdateRequest;
import store.csolved.csolved.domain.tag.Tag;

import java.util.List;

@Data
@Builder
public class PostUpdateCommand
{
    private String title;
    private String content;
    private Long authorId;
    private boolean anonymous;
    private Long views;
    private Long likes;
    private Long answerCount;
    private Long categoryId;
    private List<Tag> tags;

    public static PostUpdateCommand from(PostUpdateRequest request)
    {
        return PostUpdateCommand.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .authorId(request.getAuthorId())
                .anonymous(request.isAnonymous())
                .views(0L)
                .likes(0L)
                .answerCount(0L)
                .categoryId(request.getCategoryId())
                .tags(request.getTagList())
                .build();
    }

}
