package store.csolved.csolved.domain.question.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.common.filter.Filtering;
import store.csolved.csolved.common.page.PaginationUtils;
import store.csolved.csolved.common.search.Searching;
import store.csolved.csolved.common.sort.Sorting;
import store.csolved.csolved.domain.answer.service.dto.AnswerWithComments;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.category.service.dto.CategoryDTO;
import store.csolved.csolved.domain.category.service.CategoryService;
import store.csolved.csolved.common.page.Pagination;
import store.csolved.csolved.domain.question.controller.dto.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionCreateUpdateVM;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionDetailVM;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionListViewModel;
import store.csolved.csolved.domain.question.entity.Question;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionFacade
{
    private final QuestionService questionService;
    private final TagService tagService;
    private final AnswerService answerService;
    private final CategoryService categoryService;

    private final PaginationUtils paginationUtils;

    // 질문글, 질문글의 태그 저장.
    public void save(QuestionCreateUpdateForm form)
    {
        Long saveId = questionService.save(form.getQuestion());
        tagService.saveTags(saveId, form.getTags());
    }

    // 최초 질문글 작성 viewModel 제공
    public QuestionCreateUpdateVM initCreate()
    {
        List<CategoryDTO> categories = categoryService.getCategories();
        return QuestionCreateUpdateVM.of(categories);
    }

    // 질문글 업데이트 시 기존 질문글 제공
    public QuestionCreateUpdateForm initUpdate(Long questionId)
    {
        Question question = questionService.getQuestion(questionId);
        return QuestionCreateUpdateForm.of(question);
    }

    // 질문글 업데이트
    @Transactional
    public void update(Long questionId, QuestionCreateUpdateForm form)
    {
        questionService.update(questionId, form.getQuestion());
        tagService.updateTags(questionId, form.getTags());
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
    public QuestionListViewModel getQuestions(Long pageNumber,
                                              Sorting sort,
                                              Filtering filter,
                                              Searching search)
    {
        // DB에서 질문글 개수를 가져옴.
        Long total = questionService.countQuestions(filter, search);

        // 사용자가 요청한 페이지 번호, 질문글 개수를 사용하여 페이지 정보를 생성.
        Pagination page = paginationUtils.createPagination(pageNumber, total);

        // 페이지 정보를 사용해서 DB에 필요한 질문글만 조회.
        List<Question> questions = questionService.getQuestions(
                page,
                sort,
                filter,
                search);

        // 카테고리 정보를 모두 가져옴.
        List<CategoryDTO> categories = categoryService.getCategories();

        // 모든 데이터를 사용하여 viewModel 생성 후 반환
        return QuestionListViewModel.of(page, categories, questions);
    }

    // 상세 질문글, 태그, 답변, 댓글 조회
    public QuestionDetailVM getQuestion(Long questionId)
    {
        Question question = questionService.viewQuestion(questionId);
        List<AnswerWithComments> answers = answerService.getAnswersWithComments(questionId);
        return QuestionDetailVM.of(question, answers);
    }
}