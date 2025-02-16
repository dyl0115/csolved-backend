package store.csolved.csolved.domain.post.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.answer.Answer;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.bookmark.service.BookmarkService;
import store.csolved.csolved.domain.category.Category;
import store.csolved.csolved.domain.category.service.CategoryService;
import store.csolved.csolved.domain.comment.Comment;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.post.community.controller.form.CommunityCreateUpdateForm;
import store.csolved.csolved.domain.post.community.controller.view_model.CommunityCreateUpdateVM;
import store.csolved.csolved.domain.post.community.controller.view_model.CommunityDetailVM;
import store.csolved.csolved.domain.post.community.controller.view_model.CommunityListVM;
import store.csolved.csolved.domain.post.community.Community;
import store.csolved.csolved.utils.filter.Filtering;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.page.PaginationManager;
import store.csolved.csolved.utils.search.Searching;
import store.csolved.csolved.utils.sort.Sorting;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.List;
import java.util.Map;

import static store.csolved.csolved.common.PostType.COMMUNITY;

@RequiredArgsConstructor
@Service
public class CommunityFacade
{
    private final CommunityService communityService;
    private final BookmarkService bookmarkService;
    private final AnswerService answerService;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final TagService tagService;

    private final PaginationManager paginationUtils;

    // 커뮤니티글, 글의 태그 저장.
    public void save(CommunityCreateUpdateForm form)
    {
        Long saveId = communityService.save(form.getCommunity());
        tagService.saveTags(saveId, form.getTagList());
    }

    // 커뮤니티글 작성 시 viewModel 제공
    public CommunityCreateUpdateVM initCreate()
    {
        List<Category> categories = categoryService.getAll(COMMUNITY.getCode());
        return CommunityCreateUpdateVM.from(categories);
    }

    // 커뮤니티글 업데이트 시 viewModel 제공
    public CommunityCreateUpdateVM initUpdate()
    {
        List<Category> categories = categoryService.getAll(COMMUNITY.getCode());
        return CommunityCreateUpdateVM.from(categories);
    }

    // 커뮤니티글 업데이트 시 기존 게시글 제공
    public CommunityCreateUpdateForm initUpdateForm(Long communityId)
    {
        Community community = communityService.getCommunity(communityId);
        return CommunityCreateUpdateForm.from(community);
    }

    // 커뮤니티글 업데이트
    @Transactional
    public void update(Long postId, CommunityCreateUpdateForm form)
    {
        communityService.update(postId, form.getCommunity());
        tagService.updateTags(postId, form.getTagList());
    }

    // 커뮤니티글 좋아요
    public boolean addLike(Long postId, Long userId)
    {
        return communityService.addLike(postId, userId);
    }

    // 커뮤니티글 삭제
    public void delete(Long postId)
    {
        communityService.delete(postId);
    }

    // 커뮤니티글 리스트 조회
    public CommunityListVM getCommunityPosts(Long pageNumber,
                                             Sorting sort,
                                             Filtering filter,
                                             Searching search)
    {
        // DB에서 커뮤니티글 개수를 가져옴
        Long total = communityService.countCommunities(filter, search);

        // 사용자가 요청한 페이지 번호, 글 개수를 사용하여 페이지 정보를 생성
        Pagination page = paginationUtils.createPagination(pageNumber, total);

        // 페이지 정보를 사용하여 DB에 필요한 커뮤니티글만 조회
        List<Community> communities = communityService.getCommunities(page, sort, filter, search);

        // 카테고리의 정보를 모두 가져옴.
        List<Category> categories = categoryService.getAll(COMMUNITY.getCode());

        // 모든 데이터를 사용하여 viewModel 생성 후 반환
        return CommunityListVM.from(page, categories, communities);
    }

    // 커뮤니티글 상세 조회
    public CommunityDetailVM getCommunityPost(Long userId, Long postId)
    {
        Community community = communityService.viewCommunity(postId);
        boolean bookmarked = bookmarkService.hasBookmarked(userId, postId);
        List<Answer> answers = answerService.getAnswers(postId);
        Map<Long, List<Comment>> comments = commentService.getComments(extractIds(answers));
        return CommunityDetailVM.from(community, bookmarked, answers, comments);
    }

    // 커뮤니티글 속 답변들의 id를 추출
    private List<Long> extractIds(List<Answer> answers)
    {
        return answers.stream()
                .map(Answer::getId)
                .toList();
    }
}