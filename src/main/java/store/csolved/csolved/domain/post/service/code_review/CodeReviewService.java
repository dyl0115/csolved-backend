package store.csolved.csolved.domain.post.service.code_review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.domain.post.entity.code_review.CodeReview;
import store.csolved.csolved.domain.post.mapper.code_review.CodeReviewMapper;

@RequiredArgsConstructor
@Service
public class CodeReviewService
{
    private final CodeReviewMapper codeReviewMapper;

    public void save(CodeReview codeReview)
    {
        
    }
}
