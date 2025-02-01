package store.csolved.csolved.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.csolved.csolved.domain.tag.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post
{
    private Long id;
    private int postType;
    private String title;
    private boolean anonymous;
    private String content;
    private Long views;
    private Long likes;
    private Long answerCount;
    private LocalDateTime createdAt;
    private Long authorId;
    private String authorNickname;
    private Long categoryId;
    private String categoryName;
    private List<Tag> tags;
}
