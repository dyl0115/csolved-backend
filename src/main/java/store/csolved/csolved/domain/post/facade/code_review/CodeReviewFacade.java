package store.csolved.csolved.domain.post.facade.code_review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.domain.post.service.code_review.CodeReviewService;

@RequiredArgsConstructor
@Service
public class CodeReviewFacade
{
    private final CodeReviewService codeReviewService;

    public void save()
    {
        
    }
}
