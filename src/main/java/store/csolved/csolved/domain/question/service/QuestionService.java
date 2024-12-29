package store.csolved.csolved.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.category.mapper.CategoryMapper;
import store.csolved.csolved.domain.question.Page;
import store.csolved.csolved.domain.question.Question;
import store.csolved.csolved.domain.question.dto.QuestionFormRequest;
import store.csolved.csolved.domain.question.dto.QuestionCreateDto;
import store.csolved.csolved.domain.question.dto.QuestionListDto;
import store.csolved.csolved.domain.question.dto.QuestionDto;
import store.csolved.csolved.domain.question.mapper.QuestionMapper;
import store.csolved.csolved.domain.tag.Tag;
import store.csolved.csolved.domain.tag.mapper.TagMapper;
import store.csolved.csolved.domain.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService
{
    private final QuestionMapper questionMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;

    public void provideQuestionForm(User user, QuestionCreateDto form)
    {
        form.setUserId(user.getId());
        form.setNickname(user.getNickname());
        form.setCategoryList(categoryMapper.findAllCategory());
    }

    @Transactional
    public void saveQuestions(User user, QuestionFormRequest form)
    {
        Question question = form.toQuestion(user.getId());
        questionMapper.insertQuestion(question);
        String[] tagNames = form.getTags().split(",");
        for (String tagName : tagNames)
        {
            Tag tag = Tag.create(tagName);
            tagMapper.insertTag(tag);
        }

        for (String tagName : tagNames)
        {
            Tag tag = tagMapper.findTagByName(tagName);
            tagMapper.insertQuestionAndTag(question.getId(), tag.getId());
        }
    }

    public void provideQuestions(User user, QuestionListDto form, Page page)
    {
        List<QuestionDto> questionList = questionMapper.findAllQuestions(page.getOffset(), page.getLimit());
        form.setUser(user);
        form.setQuestionList(questionList);
    }
}
