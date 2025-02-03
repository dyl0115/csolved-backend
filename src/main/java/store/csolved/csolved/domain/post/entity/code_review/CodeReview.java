package store.csolved.csolved.domain.post.entity.code_review;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.post.entity.Post;

@Getter
@Builder
public class CodeReview extends Post
{
    private String githubUrl;
}