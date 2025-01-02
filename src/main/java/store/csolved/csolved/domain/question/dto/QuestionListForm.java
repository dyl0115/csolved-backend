package store.csolved.csolved.domain.question.dto;

import lombok.Data;
import store.csolved.csolved.domain.category.Category;
import store.csolved.csolved.domain.user.User;

import java.util.List;

@Data
public class QuestionListForm
{
    private User user;

    private List<QuestionDto> questionList;

    private List<Category> categoryList;
}
