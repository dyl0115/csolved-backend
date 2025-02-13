package store.csolved.csolved.domain.post.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.post.entity.Question;
import store.csolved.csolved.domain.post.service.QuestionService;
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
import store.csolved.csolved.domain.post.controller.question.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.post.controller.question.view_model.QuestionCreateUpdateVM;
import store.csolved.csolved.domain.post.controller.question.view_model.QuestionDetailVM;
import store.csolved.csolved.domain.post.controller.question.view_model.QuestionListVM;
import store.csolved.csolved.domain.post.entity.Post;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.List;
import java.util.Map;

import static store.csolved.csolved.domain.post.entity.PostType.QUESTION;

@RequiredArgsConstructor
@Service
public class QuestionFacade
{
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final TagService tagService;

    private final PaginationUtils paginationUtils;

    // 질문글, 질문글의 태그 저장.
    public void save(QuestionCreateUpdateForm form)
    {
        Long savedId = questionService.save(form.getQuestion());
        tagService.saveTags(savedId, form.getTagList());
    }

    // 질문글 생성 및 업데이트 시 기존 viewModel 제공
    public QuestionCreateUpdateVM initCreateUpdate()
    {
        List<Category> categories = categoryService.getAll(QUESTION.getCode());
        return QuestionCreateUpdateVM.from(categories);
    }

    // 질문글 업데이트 시 기존 게시글 제공
    public QuestionCreateUpdateForm initUpdateForm(Long questionId)
    {
        Question question = questionService.getQuestion(questionId);
        return QuestionCreateUpdateForm.from(question);
    }

    // 질문글 업데이트
    @Transactional
    public void update(Long questionId, QuestionCreateUpdateForm form)
    {
        questionService.update(questionId, form.getQuestion());
        tagService.updateTags(questionId, form.getTagList());
    }

    // 질문글 좋아요
    public boolean addLike(Long questionId, Long userId)
    {
        return questionService.addLike(questionId, userId);
    }

    // 질문글 삭제
    public void delete(Long questionId)
    {
        questionService.delete(questionId);
    }

    // 질문글 리스트 조회
    public QuestionListVM getQuestions(Long pageNumber,
                                       Sorting sort,
                                       Filtering filter,
                                       Searching search)
    {
        // DB에서 질문글 개수를 가져옴.
        Long total = questionService.countQuestions(filter, search);

        // 사용자가 요청한 페이지 번호, 질문글 개수를 사용하여 페이지 정보를 생성.
        Pagination page = paginationUtils.createPagination(pageNumber, total);

        // 페이지 정보를 사용하여 DB로부터 필요한 질문글만 조회.
        List<Question> questions = questionService.getPosts(page, sort, filter, search);

        // 카테고리 정보를 모두 가져옴.
        List<Category> categories = categoryService.getAll(QUESTION.getCode());

        // 모든 데이터를 사용하여 viewModel 생성 후 반환
        return QuestionListVM.from(page, categories, questions);
    }

    // 질문글 상세 조회
    public QuestionDetailVM getQuestion(Long postId)
    {
        Question question = questionService.viewPost(postId);
        List<Answer> answers = answerService.getAnswers(postId);
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