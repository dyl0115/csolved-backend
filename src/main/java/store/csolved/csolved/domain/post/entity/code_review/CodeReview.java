package store.csolved.csolved.domain.post.entity.code_review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.domain.post.entity.Post;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CodeReview extends Post
{
    private String githubUrl;
}