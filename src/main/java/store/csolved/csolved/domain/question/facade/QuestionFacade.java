package store.csolved.csolved.domain.question.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.common.search.FilterRequest;
import store.csolved.csolved.common.search.SortType;
import store.csolved.csolved.domain.answer.service.dto.AnswerWithCommentsDTO;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.category.service.dto.CategoryDetailDTO;
import store.csolved.csolved.domain.category.service.CategoryService;
import store.csolved.csolved.common.search.PageDetailDTO;
import store.csolved.csolved.domain.question.controller.dto.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionCreateUpdateViewModel;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionDetailViewModel;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionListViewModel;
import store.csolved.csolved.domain.question.service.dto.QuestionSummaryDTO;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.question.service.dto.QuestionDetailDTO;
import store.csolved.csolved.domain.tag.dto.TagNameDTO;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.ArrayList;
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
    public QuestionListViewModel getQuestions(Long requestPage,
                                              SortType sortInfo,
                                              FilterRequest filterInfo)
    {
        Long questionsCount = questionService.getQuestionsCount(filterInfo);

        PageDetailDTO page = PageDetailDTO.create(requestPage, questionsCount, DEFAULT_QUESTION_COUNT_ON_SINGLE_PAGE);

        List<QuestionDetailDTO> questions = questionService.getQuestions(page, sortInfo, filterInfo);
        Map<Long, List<TagNameDTO>> tagMap = tagService.getTags(new ArrayList<>(questions.stream().map(QuestionDetailDTO::getId).toList()));
        List<CategoryDetailDTO> categories = categoryService.getAllCategories();
        List<QuestionSummaryDTO> questionSummary = QuestionSummaryDTO.from(questions, tagMap);

        return QuestionListViewModel.from(page, categories, questionSummary);
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
