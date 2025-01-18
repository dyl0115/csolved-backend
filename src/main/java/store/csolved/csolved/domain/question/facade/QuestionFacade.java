package store.csolved.csolved.domain.question.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.common.filter.FilterRequest;
import store.csolved.csolved.common.search.SearchRequest;
import store.csolved.csolved.common.sort.SortType;
import store.csolved.csolved.domain.answer.service.dto.AnswerWithCommentsDTO;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.category.service.dto.CategoryDetailDTO;
import store.csolved.csolved.domain.category.service.CategoryService;
import store.csolved.csolved.common.page.PageDetailDTO;
import store.csolved.csolved.domain.question.controller.dto.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionCreateUpdateViewModel;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionDetailViewModel;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionListViewModel;
import store.csolved.csolved.domain.question.service.dto.QuestionSummaryDTO;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.question.service.dto.QuestionDetailDTO;
import store.csolved.csolved.domain.tag.dto.TagNameDTO;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class QuestionFacade
{
    private final static Long DEFAULT_QUESTION_COUNT_ON_SINGLE_PAGE = 7L;

    private final QuestionService questionService;
    private final TagService tagService;
    private final AnswerService answerService;
    private final CategoryService categoryService;

    // 질문글, 질문글의 태그 저장.
    public void saveQuestion(Long userId, QuestionCreateUpdateForm form)
    {
        Long questionIdSaved = questionService.saveQuestion(userId, form);
        tagService.saveTags(questionIdSaved, form);
    }

    // 최초 질문글 작성 viewModel 제공
    public QuestionCreateUpdateViewModel getQuestionCreateUpdateViewModel()
    {
        List<CategoryDetailDTO> categories = categoryService.getAllCategories();
        return QuestionCreateUpdateViewModel.from(categories);
    }

    // 질문글 업데이트 시 기존 질문글 제공
    public QuestionCreateUpdateForm getQuestionUpdateForm(Long questionId)
    {
        return questionService.getQuestionUpdateForm(questionId);
    }

    // 질문글 업데이트
    @Transactional
    public void updateQuestion(Long questionId, Long userId, QuestionCreateUpdateForm form)
    {
        questionService.updateQuestion(questionId, userId, form);
        tagService.updateTags(questionId, form);
    }

    public boolean increaseQuestionLikes(Long questionId, Long userId)
    {
        return questionService.increaseLike(questionId, userId);
    }

    public void deleteQuestion(Long questionId)
    {
        questionService.deleteQuestion(questionId);
    }

    // 질문글 리스트 조회
    public QuestionListViewModel getQuestions(Long requestedPageNumber,
                                              SortType sortType,
                                              FilterRequest filter,
                                              SearchRequest search)
    {
        // DB에서 질문글 개수를 가져옴.
        Long filteredQuestionCount = questionService.getQuestionsCount(filter, search);

        // 사용자가 요청한 페이지 번호, 질문글 개수를 사용하여 페이지 정보를 생성.
        PageDetailDTO pageInfo = PageDetailDTO.create(
                requestedPageNumber,
                filteredQuestionCount,
                DEFAULT_QUESTION_COUNT_ON_SINGLE_PAGE);

        // 페이지 정보를 사용해서 DB에 필요한 질문글만 조회.
        List<QuestionDetailDTO> pagedQuestions = questionService.getQuestions(
                pageInfo,
                sortType,
                filter,
                search);

        // 페이징된 질문글과 관련된 태그를 모두 가져옴.
        Map<Long, List<TagNameDTO>> questionTagsMap = tagService.getTags(QuestionDetailDTO.getIdList(pagedQuestions));

        // 질문글 정보, 태그 정보를 모두 가져와서 조립함.
        List<QuestionSummaryDTO> questionSummaries = QuestionSummaryDTO.from(pagedQuestions, questionTagsMap);

        // 카테고리 정보를 모두 가져옴.
        List<CategoryDetailDTO> sidebarCategories = categoryService.getAllCategories();

        // 모든 데이터를 사용하여 viewModel 생성 후 반환
        return QuestionListViewModel.from(pageInfo, sidebarCategories, questionSummaries);
    }

    // 상세 질문글, 태그, 답변, 댓글 조회
    public QuestionDetailViewModel getQuestion(Long questionId)
    {
        QuestionDetailDTO question = questionService.getQuestionWithViewIncrease(questionId);
        List<TagNameDTO> tags = tagService.getTags(questionId);
        List<AnswerWithCommentsDTO> answers = answerService.getAnswersWithComments(questionId);

        return QuestionDetailViewModel.from(question, tags, answers);
    }
}