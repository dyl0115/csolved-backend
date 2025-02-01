package store.csolved.csolved.domain.free_board.entity;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.tag.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class Article
{
    private Long id;
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
