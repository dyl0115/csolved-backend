package store.csolved.csolved.domain.post.code_review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.post.code_review.CodeReview;
import store.csolved.csolved.domain.post.code_review.mapper.CodeReviewMapper;
import store.csolved.csolved.utils.filter.Filtering;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.search.Searching;
import store.csolved.csolved.utils.sort.Sorting;

import java.util.List;

import static store.csolved.csolved.common.PostType.CODE_REVIEW;

@RequiredArgsConstructor
@Service
public class CodeReviewService
{
    private final CodeReviewMapper codeReviewMapper;

    @Transactional
    public Long save(CodeReview codeReview)
    {
        codeReviewMapper.saveCodeReview(CODE_REVIEW.getCode(), codeReview);
        return codeReview.getId();
    }

    public Long countCodeReviews(Filtering filter, Searching search)
    {
        return codeReviewMapper.countCodeReviews(
                CODE_REVIEW.getCode(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
    }

    public List<CodeReview> getCodeReviews(Pagination page,
                                           Sorting sort,
                                           Filtering filter,
                                           Searching search)
    {
        return codeReviewMapper.getCodeReviews(
                CODE_REVIEW.getCode(),
                page.getOffset(),
                page.getSize(),
                sort.name(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
    }

    public CodeReview getCodeReview(Long postId)
    {
        return codeReviewMapper.getCodeReview(postId);
    }

    @Transactional
    public CodeReview viewCodeReview(Long postId)
    {
        codeReviewMapper.increaseView(postId);
        return codeReviewMapper.getCodeReview(postId);
    }

    @Transactional
    public void update(Long postId, CodeReview codeReview)
    {
        codeReviewMapper.updateCodeReview(postId, codeReview);
    }

    @Transactional
    public void delete(Long codeReviewId)
    {
        codeReviewMapper.deleteCodeReview(codeReviewId);
    }

    @Transactional
    public boolean addLike(Long codeReviewId, Long userId)
    {
        if (codeReviewMapper.hasUserLiked(codeReviewId, userId))
        {
            return false;
        }

        codeReviewMapper.addUserLike(codeReviewId, userId);
        codeReviewMapper.increaseLikes(codeReviewId);
        return true;
    }
}
