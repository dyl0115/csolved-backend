package store.csolved.csolved.domain.code_review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.Post;
import store.csolved.csolved.domain.tag.Tag;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CodeReview extends Post
{
    private Long categoryId;
    private String categoryName;
    private List<Tag> tags;
    private String githubUrl;
    private Long views;
    private Long likes;
    private Long answerCount;
}