package store.csolved.csolved.domain.post.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.category.entity.Category;
import store.csolved.csolved.domain.category.service.CategoryService;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.post.controller.code_review.form.CodeReviewCreateUpdateForm;
import store.csolved.csolved.domain.post.controller.code_review.view_model.CodeReviewCreateUpdateVM;
import store.csolved.csolved.domain.post.controller.code_review.view_model.CodeReviewDetailVM;
import store.csolved.csolved.domain.post.controller.code_review.view_model.CodeReviewListVM;
import store.csolved.csolved.domain.post.entity.CodeReview;
import store.csolved.csolved.domain.post.service.old.PostService;
import store.csolved.csolved.domain.post.service.CodeReviewService;
import store.csolved.csolved.domain.search.filter.Filtering;
import store.csolved.csolved.domain.search.page.Pagination;
import store.csolved.csolved.domain.search.page.PaginationUtils;
import store.csolved.csolved.domain.search.search.Searching;
import store.csolved.csolved.domain.search.sort.Sorting;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.List;
import java.util.Map;

import static store.csolved.csolved.domain.post.entity.PostType.*;

@RequiredArgsConstructor
@Service
public class CodeReviewFacade
{
    private final CodeReviewService codeReviewService;
    private final AnswerService answerService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final PaginationUtils paginationUtils;
    private final PostService postService;
    private final CommentService commentService;

    public CodeReviewCreateUpdateForm initCreate()
    {
        return CodeReviewCreateUpdateForm.empty();
    }

    // TODO Facade 계층에 @Transaction?  대대적인 리팩토링이 필요한듯하다.
    // TODO 내부 tagService에서 @Transaction을 사용하는데, 중첩해도 좋을까?
    // 코드 리뷰 글, 태그 저장
    @Transactional
    public void save(CodeReviewCreateUpdateForm form)
    {
        Long saveId = codeReviewService.saveCodeReview(form.getCodeReview());
        tagService.saveTags(saveId, form.getTagList());
    }

    public CodeReviewCreateUpdateVM initCreateUpdate()
    {
        List<Category> categories = categoryService.getAll(CODE.getCode());
        return CodeReviewCreateUpdateVM.from(categories);
    }

    public CodeReviewListVM getCodeReviews(Long pageNumber,
                                           Sorting sort,
                                           Filtering filter,
                                           Searching search)
    {
        // DB에서 코드리뷰 글의 개수를 조회
        Long total = codeReviewService.countCodeReviews(filter, search);

        // 사용자가 요청한 페이지 번호, 게시글 개수를 사용하여 페이지 정보 생성
        Pagination page = paginationUtils.createPagination(pageNumber, total);

        // 페이지 정보를 사용하여 DB로부터 필요한 게시글만 조회
        List<CodeReview> codeReviews = codeReviewService.getCodeReviews(page, sort, filter, search);

        // 카테고리 정보를 모두 가져옴
        List<Category> categories = categoryService.getAll(CODE.getCode());

        // 모든 데이터를 사용하여 viewModel 생성 후 반환
        return CodeReviewListVM.from(page, categories, codeReviews);
    }

    // 게시글 상세조회
    public CodeReviewDetailVM getCodeReview(Long postId)
    {
        CodeReview codeReview = codeReviewService.viewCodeReview(postId);
        List<Answer> answers = answerService.getAnswers(postId);
        Map<Long, List<Comment>> comments = commentService.getComments(extractIds(answers));
        return CodeReviewDetailVM.from(codeReview, answers, comments);
    }

    // 게시글 속 답변들의 id를 추출
    private List<Long> extractIds(List<Answer> answers)
    {
        return answers.stream()
                .map(Answer::getId)
                .toList();
    }

    // 게시글 업데이트
    @Transactional
    public void update(Long postId,
                       CodeReviewCreateUpdateForm form)
    {
        codeReviewService.updateCodeReview(postId, form.getCodeReview());
        tagService.updateTags(postId, form.getTagList());
    }

    public CodeReviewCreateUpdateForm initUpdateForm(Long postId)
    {
        CodeReview codeReview = codeReviewService.getCodeReview(postId);
        return CodeReviewCreateUpdateForm.from(codeReview);
    }

    // 게시글 좋아요
    @Transactional
    public boolean addLike(Long postId, Long userId)
    {
        return postService.addLike(postId, userId);
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long postId)
    {
        postService.delete(postId);
    }
}
