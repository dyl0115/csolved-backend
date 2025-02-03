package store.csolved.csolved.domain.post.mapper.code_review;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.post.entity.code_review.CodeReview;

@Mapper
public interface CodeReviewMapper
{
    void save(CodeReview codeReview);
}
