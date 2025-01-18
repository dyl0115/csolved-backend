package store.csolved.csolved.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.common.filter.FilterRequest;
import store.csolved.csolved.common.page.PageDetailDTO;
import store.csolved.csolved.common.search.SearchRequest;
import store.csolved.csolved.common.sort.SortType;
import store.csolved.csolved.domain.question.controller.dto.form.QuestionCreateUpdateForm;
import store.csolved.csolved.domain.question.Question;
import store.csolved.csolved.domain.question.mapper.QuestionMapper;
import store.csolved.csolved.domain.question.service.dto.QuestionDetailDTO;
import store.csolved.csolved.domain.question.service.dto.record.QuestionDetailRecord;
import store.csolved.csolved.domain.tag.mapper.TagMapper;
import store.csolved.csolved.domain.tag.service.dto.TagNameRecord;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService
{
    private final QuestionMapper questionMapper;
    private final TagMapper tagMapper;

    public Long getQuestionsCount(FilterRequest filter, SearchRequest search)
    {
        return questionMapper.getQuestionsCount(
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
    }

    public List<QuestionDetailDTO> getQuestions(PageDetailDTO page,
                                                SortType sort,
                                                FilterRequest filter,
                                                SearchRequest search)
    {
        List<QuestionDetailRecord> questions = questionMapper.getQuestions(
                page.getOffset(),
                page.getSize(),
                sort.name(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());

        return QuestionDetailDTO.from(questions);
    }

    // 질문글의 조회수를 1만큼 올리고, 질문 상세를 보여줌.
    @Transactional
    public QuestionDetailDTO getQuestionWithViewIncrease(Long questionId)
    {
        questionMapper.increaseView(questionId);
        QuestionDetailRecord question = questionMapper.getQuestionDetail(questionId);
        return QuestionDetailDTO.from(question);
    }

    // 기존 질문글의 데이터를 수정폼에 담아줌.
    public QuestionCreateUpdateForm getQuestionUpdateForm(Long questionId)
    {
        QuestionDetailRecord question = questionMapper.getQuestionDetail(questionId);
        List<TagNameRecord> tags = tagMapper.getTagsByQuestionId(questionId);

        return QuestionCreateUpdateForm.from(question, tags);
    }

    @Transactional
    public Long saveQuestion(Long userId, QuestionCreateUpdateForm form)
    {
        Question question = form.toQuestion(userId);
        questionMapper.insertQuestion(question);
        return question.getId();
    }

    @Transactional
    public void updateQuestion(Long questionId, Long userId, QuestionCreateUpdateForm form)
    {
        Question question = form.toQuestion(userId);
        questionMapper.updateQuestion(questionId, question);
    }

    @Transactional
    public void deleteQuestion(Long questionId)
    {
        questionMapper.softDeleteQuestionByQuestionId(questionId);
    }

    @Transactional
    public boolean increaseLike(Long questionId, Long userId)
    {
        boolean alreadyLiked = questionMapper.existUserInQuestionLikes(questionId, userId);

        if (alreadyLiked) return false;

        questionMapper.insertUserInQuestionLikes(questionId, userId);
        questionMapper.increaseLikesInQuestions(questionId);
        return true;
    }
}
