package store.csolved.csolved.domain.post.facade.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.search.filter.Filtering;
import store.csolved.csolved.domain.search.page.PaginationUtils;
import store.csolved.csolved.domain.search.search.Searching;
import store.csolved.csolved.domain.search.sort.Sorting;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.category.entity.Category;
import store.csolved.csolved.domain.category.service.CategoryService;
import store.csolved.csolved.domain.search.page.Pagination;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.post.controller.question.dto.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.post.controller.question.dto.view_model.QuestionCreateUpdateVM;
import store.csolved.csolved.domain.post.controller.question.dto.view_model.QuestionDetailVM;
import store.csolved.csolved.domain.post.controller.question.dto.view_model.QuestionListVM;
import store.csolved.csolved.domain.post.entity.Post;
import store.csolved.csolved.domain.post.service.PostService;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.List;
import java.util.Map;

import static store.csolved.csolved.domain.post.entity.PostType.QUESTION;

@RequiredArgsConstructor
@Service
public class QuestionFacade
{
    private final PostService postService;
    private final AnswerService answerService;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final TagService tagService;

    private final PaginationUtils paginationUtils;

    // 질문글, 질문글의 태그 저장.
    public void save(QuestionCreateUpdateForm form)
    {
        Long saveId = postService.save(form.getQuestion());
        tagService.saveTags(saveId, form.getTagList());
    }

    // 질문글 작성시 viewModel 제공
    public QuestionCreateUpdateVM initCreate()
    {
        List<Category> categories = categoryService.getAll();
        return QuestionCreateUpdateVM.from(categories);
    }

    // 질문글 업데이트 시 기존 viewModel 제공
    public QuestionCreateUpdateVM initUpdate(Long questionId)
    {
        List<Category> categories = categoryService.getAll();
        return QuestionCreateUpdateVM.from(categories);
    }

    // 질문글 업데이트 시 기존 게시글 제공
    public QuestionCreateUpdateForm initUpdateForm(Long questionId)
    {
        Post question = postService.getPost(questionId);
        return QuestionCreateUpdateForm.from(question);
    }

    // 질문글 업데이트
    @Transactional
    public void update(Long questionId, QuestionCreateUpdateForm form)
    {
        postService.update(questionId, form.getQuestion());
        tagService.updateTags(questionId, form.getTagList());
    }

    // 질문글 좋아요
    public boolean addLike(Long questionId, Long userId)
    {
        return postService.addLike(questionId, userId);
    }

    // 질문글 삭제
    public void delete(Long questionId)
    {
        postService.delete(questionId);
    }

    // 질문글 리스트 조회
    public QuestionListVM getQuestions(Long pageNumber,
                                       Sorting sort,
                                       Filtering filter,
                                       Searching search)
    {
        // DB에서 질문글 개수를 가져옴.
        Long total = postService.countPosts(QUESTION.getCode(), filter, search);

        // 사용자가 요청한 페이지 번호, 질문글 개수를 사용하여 페이지 정보를 생성.
        Pagination page = paginationUtils.createPagination(pageNumber, total);

        // 페이지 정보를 사용하여 DB에 필요한 질문글만 조회.
        List<Post> questions = postService.getPosts(QUESTION.getCode(), page, sort, filter, search);

        // 카테고리 정보를 모두 가져옴.
        List<Category> categories = categoryService.getAll();

        // 모든 데이터를 사용하여 viewModel 생성 후 반환
        return QuestionListVM.from(page, categories, questions);
    }

    // 질문글 상세 조회
    public QuestionDetailVM getQuestion(Long questionId)
    {
        Post question = postService.viewPost(questionId);
        List<Answer> answers = answerService.getAnswers(questionId);
        Map<Long, List<Comment>> comments = commentService.getComments(extractIds(answers));
        return QuestionDetailVM.from(question, answers, comments);
    }

    // 질문글 속 답변들의 id를 추출
    private List<Long> extractIds(List<Answer> answers)
    {
        return answers.stream()
                .map(Answer::getId)
                .toList();
    }
}