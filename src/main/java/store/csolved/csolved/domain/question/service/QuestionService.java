package store.csolved.csolved.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.category.Category;
import store.csolved.csolved.domain.category.CategoryMapper;
import store.csolved.csolved.domain.question.Question;
import store.csolved.csolved.domain.question.dto.QuestionFormResponse;
import store.csolved.csolved.domain.question.mapper.QuestionMapper;
import store.csolved.csolved.domain.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService
{
    private final QuestionMapper questionMapper;
    private final CategoryMapper categoryMapper;

    public void provideQuestionForm(User user, QuestionFormResponse saveForm)
    {
        saveForm.setUserId(user.getId());
        saveForm.setNickname(user.getNickname());
        saveForm.setCategoryList(categoryMapper.findAllCategoryNames());
    }
}
