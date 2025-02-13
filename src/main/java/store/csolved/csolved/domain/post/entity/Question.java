package store.csolved.csolved.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.domain.tag.entity.Tag;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Question extends Post
{
    private Long categoryId;
    private String categoryName;
    private List<Tag> tags;
    private Long views;
    private Long likes;
    private Long answerCount;
}
