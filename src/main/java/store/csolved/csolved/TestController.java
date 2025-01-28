package store.csolved.csolved;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.category.entity.Category;
import store.csolved.csolved.domain.category.mapper.CategoryMapper;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.comment.service.CommentService;
import store.csolved.csolved.domain.question.controller.dto.viewModel.QuestionCreateVM;
import store.csolved.csolved.domain.tag.entity.Tag;
import store.csolved.csolved.domain.user.User;
import store.csolved.csolved.domain.user.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class TestController
{
    private final CategoryMapper categoryMapper;

    @GetMapping("/test")
    public String testView(Model model)
    {
        List<Category> categories = categoryMapper.getAll();
        User user = User.builder().build();
        SuccessStoryForm form = SuccessStoryForm.builder().build();
        model.addAttribute("createVM", QuestionCreateVM.builder().categories(categories).build());
        model.addAttribute("user", user);
        model.addAttribute("createForm", form);
        return "views/domain/success/create";
    }

    @Getter
    @Builder
    public static class SuccessStoryForm
    {
        private boolean anonymous;
        private Long authorId;
        private Long userId;
        private Long categoryId;
        private String title;
        private List<Tag> tags;


        private String company;
        private LocalDate passDate;
        private Integer preparationPeriod;
        private String periodUnit;
        private String preparationMethod;
        private String processSteps;
        private String interviewQuestions;
        private String difficulties;
        private String advice;
        // getters, setters
    }
}
