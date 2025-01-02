package store.csolved.csolved.domain.question.dto;

import lombok.Getter;
import lombok.Setter;
import store.csolved.csolved.domain.category.Category;
import store.csolved.csolved.domain.user.User;

import java.util.List;

@Getter
@Setter
public class QuestionCreateForm
{
    private String title;

    private User user;

    private String content;

    private List<Category> categoryList;
}
