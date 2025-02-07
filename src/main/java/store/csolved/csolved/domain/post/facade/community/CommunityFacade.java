package store.csolved.csolved.domain.post.facade.community;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.category.entity.Category;
import store.csolved.csolved.domain.category.service.CategoryService;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.post.controller.community.dto.form.CommunityCreateUpdateForm;
import store.csolved.csolved.domain.post.controller.community.dto.view_model.CommunityCreateUpdateVM;
import store.csolved.csolved.domain.post.controller.community.dto.view_model.CommunityDetailVM;
import store.csolved.csolved.domain.post.controller.community.dto.view_model.CommunityListVM;
import store.csolved.csolved.domain.post.entity.Post;
import store.csolved.csolved.domain.post.service.PostService;
import store.csolved.csolved.domain.search.filter.Filtering;
import store.csolved.csolved.domain.search.page.Pagination;
import store.csolved.csolved.domain.search.page.PaginationUtils;
import store.csolved.csolved.domain.search.search.Searching;
import store.csolved.csolved.domain.search.sort.Sorting;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.List;
import java.util.Map;

import static store.csolved.csolved.domain.post.entity.PostType.COMMUNITY;

@RequiredArgsConstructor
@Service
public class CommunityFacade
{
    private final PostService postService;
    private final AnswerService answerService;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final TagService tagService;

    private final PaginationUtils paginationUtils;

    // 커뮤니티글, 글의 태그 저장.
    public void save(CommunityCreateUpdateForm form)
    {
        Long saveId = postService.save(form.getCommunity());
        tagService.saveTags(saveId, form.getTagList());
    }

    // 커뮤니티글 작성 시 viewModel 제공
    public CommunityCreateUpdateVM initCreate()
    {
        List<Category> categories = categoryService.getAll(COMMUNITY.getCode());
        return CommunityCreateUpdateVM.from(categories);
    }

    // 커뮤니티글 업데이트 시 viewModel 제공
    public CommunityCreateUpdateVM initUpdate(Long postId)
    {
        List<Category> categories = categoryService.getAll(COMMUNITY.getCode());
        return CommunityCreateUpdateVM.from(categories);
    }

    // 커뮤니티글 업데이트 시 기존 게시글 제공
    public CommunityCreateUpdateForm initUpdateForm(Long postId)
    {
        Post post = postService.getPost(postId);
        return CommunityCreateUpdateForm.from(post);
    }

    // 커뮤니티글 업데이트
    @Transactional
    public void update(Long postId, CommunityCreateUpdateForm form)
    {
        postService.update(postId, form.getCommunity());
        tagService.updateTags(postId, form.getTagList());
    }

    // 커뮤니티글 좋아요
    public boolean addLike(Long postId, Long userId)
    {
        return postService.addLike(postId, userId);
    }

    // 커뮤니티글 삭제
    public void delete(Long postId)
    {
        postService.delete(postId);
    }

    // 커뮤니티글 리스트 조회
    public CommunityListVM getCommunityPosts(Long pageNumber,
                                             Sorting sort,
                                             Filtering filter,
                                             Searching search)
    {
        // DB에서 커뮤니티글 개수를 가져옴
        Long total = postService.countPosts(COMMUNITY.getCode(), filter, search);

        // 사용자가 요청한 페이지 번호, 글 개수를 사용하여 페이지 정보를 생성
        Pagination page = paginationUtils.createPagination(pageNumber, total);

        // 페이지 정보를 사용하여 DB에 필요한 커뮤니티글만 조회
        List<Post> posts = postService.getPosts(COMMUNITY.getCode(), page, sort, filter, search);

        // 카테고리의 정보를 모두 가져옴.
        List<Category> categories = categoryService.getAll(COMMUNITY.getCode());

        // 모든 데이터를 사용하여 viewModel 생성 후 반환
        return CommunityListVM.from(page, categories, posts);
    }

    // 커뮤니티글 상세 조회
    public CommunityDetailVM getCommunityPost(Long postId)
    {
        Post post = postService.viewPost(postId);
        List<Answer> answers = answerService.getAnswers(postId);
        Map<Long, List<Comment>> comments = commentService.getComments(extractIds(answers));
        return CommunityDetailVM.from(post, answers, comments);
    }

    // 커뮤니티글 속 답변들의 id를 추출
    private List<Long> extractIds(List<Answer> answers)
    {
        return answers.stream()
                .map(Answer::getId)
                .toList();
    }
}