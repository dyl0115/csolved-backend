package store.csolved.csolved.domain.post.facade;

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
import store.csolved.csolved.domain.post.controller.dto.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.post.controller.dto.viewModel.QuestionCreateVM;
import store.csolved.csolved.domain.post.controller.dto.viewModel.QuestionDetailVM;
import store.csolved.csolved.domain.post.controller.dto.viewModel.QuestionListViewModel;
import store.csolved.csolved.domain.post.controller.dto.viewModel.QuestionUpdateVM;
import store.csolved.csolved.domain.post.entity.Question;
import store.csolved.csolved.domain.post.service.QuestionService;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class QuestionFacade
{
    private final QuestionService questionService;
    private final TagService tagService;
    private final AnswerService answerService;
    private final CategoryService categoryService;

    private final PaginationUtils paginationUtils;
    private final CommentService commentService;

    // 질문글, 질문글의 태그 저장.
    public void save(QuestionCreateUpdateForm form)
    {
        Long saveId = questionService.save(form.getQuestion());
        tagService.saveTags(saveId, form.getTagList());
    }

    // 최초 질문글 작성 viewModel 제공
    public QuestionCreateVM initCreate()
    {
        List<Category> categories = categoryService.getAll();
        return QuestionCreateVM.from(categories);
    }

    // 질문글 업데이트 시 기존 질문글 제공
    public QuestionUpdateVM initUpdate(Long questionId)
    {
        List<Category> categories = categoryService.getAll();
        return QuestionUpdateVM.from(categories);
    }

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

    public boolean addLike(Long questionId, Long userId)
    {
        return questionService.addLike(questionId, userId);
    }

    public void delete(Long questionId)
    {
        questionService.delete(questionId);
    }

    // 질문글 리스트 조회
    public QuestionListViewModel getQuestions(Long pageNumber, Sorting sort,
                                              Filtering filter, Searching search)
    {
        // DB에서 질문글 개수를 가져옴.
        Long total = questionService.countQuestions(filter, search);

        // 사용자가 요청한 페이지 번호, 질문글 개수를 사용하여 페이지 정보를 생성.
        Pagination page = paginationUtils.createPagination(pageNumber, total);

        // 페이지 정보를 사용해서 DB에 필요한 질문글만 조회.
        List<Question> questions = questionService.getQuestions(page, sort, filter, search);

        // 카테고리 정보를 모두 가져옴.
        List<Category> categories = categoryService.getAll();

        // 모든 데이터를 사용하여 viewModel 생성 후 반환
        return QuestionListViewModel.of(page, categories, questions);
    }

    // 상세 질문글, 태그, 답변, 댓글 조회
    public QuestionDetailVM getQuestion(Long questionId)
    {
        Question question = questionService.viewQuestion(questionId);
        List<Answer> answers = answerService.getAnswers(questionId);
        Map<Long, List<Comment>> comments = commentService.getComments(extractIds(answers));
        return QuestionDetailVM.of(question, answers, comments);
    }

    private List<Long> extractIds(List<Answer> answers)
    {
        return answers.stream()
                .map(Answer::getId)
                .toList();
    }
}