package store.csolved.csolved.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.category.mapper.CategoryMapper;
import store.csolved.csolved.domain.question.Page;
import store.csolved.csolved.domain.question.Question;
import store.csolved.csolved.domain.question.dto.QuestionSaveForm;
import store.csolved.csolved.domain.question.dto.QuestionCreateForm;
import store.csolved.csolved.domain.question.dto.QuestionListForm;
import store.csolved.csolved.domain.question.dto.QuestionDto;
import store.csolved.csolved.domain.question.mapper.QuestionMapper;
import store.csolved.csolved.domain.tag.service.TagService;
import store.csolved.csolved.domain.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService
{
    private final QuestionMapper questionMapper;
    private final CategoryMapper categoryMapper;
    private final TagService tagService;

    public void provideQuestions(User user, QuestionListForm form, Page page)
    {
        List<QuestionDto> questionList = questionMapper.findAllQuestions(page.getOffset(), page.getLimit());
        form.setUser(user);
        form.setQuestionList(questionList);
    }

    public void provideQuestionForm(User user, QuestionCreateForm form)
    {
        form.setUser(user);
        form.setCategoryList(categoryMapper.findAllCategory());
    }

    @Transactional
    public void saveQuestion(User user, QuestionSaveForm form)
    {
        form.setUser(user);
        Question question = form.toQuestion();
        questionMapper.insertQuestion(question);

        tagService.saveAndGetTags(form.getTags())
                .forEach(tag -> questionMapper.insertQuestionAndTag(question.getId(), tag.getId()));
    }
}
