package store.csolved.csolved.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.question.Page;
import store.csolved.csolved.domain.question.Question;
import store.csolved.csolved.domain.question.dto.QuestionCreateForm;
import store.csolved.csolved.domain.question.dto.QuestionDto;
import store.csolved.csolved.domain.question.mapper.QuestionMapper;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService
{
    private final QuestionMapper questionMapper;
    private final TagService tagService;

    public List<QuestionDto> provideQuestions(Page page)
    {
        return questionMapper.findAllQuestions(0L, 10L);
    }

    public QuestionDto provideQuestion(Long questionId)
    {
        return questionMapper.findQuestionByQuestionId(questionId);
    }

    @Transactional
    public void saveQuestion(QuestionCreateForm form)
    {
        Question question = form.toQuestion();
        questionMapper.insertQuestion(question);

        tagService.saveAndGetTags(form.getTags()).forEach(tag -> questionMapper.insertQuestionAndTag(question.getId(), tag.getId()));
    }

    @Transactional
    public void deleteQuestion(Long questionId)
    {
        questionMapper.softDeleteQuestionByQuestionId(questionId);
    }

    public boolean hasAlreadyLiked(Long questionId, Long userId)
    {
        return questionMapper.existUserInQuestionLikes(questionId, userId);
    }

    @Transactional
    public void increaseLike(Long questionId, Long userId)
    {
        questionMapper.insertUserInQuestionLikes(questionId, userId);
        questionMapper.increaseLikesInQuestions(questionId);
    }

    @Transactional
    public void increaseView(Long questionId)
    {
        questionMapper.increaseViewInQuestions(questionId);
    }
}
