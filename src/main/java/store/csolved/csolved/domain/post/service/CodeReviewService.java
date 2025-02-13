package store.csolved.csolved.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.post.entity.CodeReview;
import store.csolved.csolved.domain.post.mapper.old.PostMapper;
import store.csolved.csolved.domain.post.mapper.CodeReviewMapper;
import store.csolved.csolved.domain.search.filter.Filtering;
import store.csolved.csolved.domain.search.page.Pagination;
import store.csolved.csolved.domain.search.search.Searching;
import store.csolved.csolved.domain.search.sort.Sorting;

import java.util.List;

import static store.csolved.csolved.domain.post.entity.PostType.CODE;

@RequiredArgsConstructor
@Service
public class CodeReviewService
{
    private final PostMapper postMapper;
    private final CodeReviewMapper codeReviewMapper;

    @Transactional
    public Long saveCodeReview(CodeReview codeReview)
    {
        codeReviewMapper.saveCodeReview(codeReview);
        Long savedId = codeReview.getId();

        codeReviewMapper.saveGithubUrl(savedId, codeReview.getGithubUrl());
        return savedId;
    }

    public Long countCodeReviews(Filtering filter, Searching search)
    {
        return codeReviewMapper.countCodeReviews(
                CODE.getCode(),
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
                CODE.getCode(),
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
        postMapper.increaseView(postId);
        return codeReviewMapper.getCodeReview(postId);
    }

    @Transactional
    public void updateCodeReview(Long postId, CodeReview codeReview)
    {
        postMapper.updatePost(postId, codeReview);
        codeReviewMapper.updateGithubUrl(postId, codeReview.getGithubUrl());
    }
}
