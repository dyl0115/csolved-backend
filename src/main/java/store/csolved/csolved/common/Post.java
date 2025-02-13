package store.csolved.csolved.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Post
{
    private Long id;
    //    private int postType;
    private String title;
    private boolean anonymous;
    private Long authorId;
    private String authorNickname;
    private String content;
    private LocalDateTime createdAt;
//    private Long views;
//    private Long likes;
//    private Long categoryId;
//    private String categoryName;
//    private List<Tag> tags;
//    private Long answerCount;
}
