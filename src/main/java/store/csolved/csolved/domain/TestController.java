package store.csolved.csolved.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.csolved.csolved.auth.annotation.LoginUser;
import store.csolved.csolved.domain.common.Page;
import store.csolved.csolved.domain.question.service.QuestionService;
import store.csolved.csolved.domain.user.User;

@RequiredArgsConstructor
@RequestMapping("/test")
@Controller
public class TestController
{
    private final QuestionService questionService;

    @GetMapping
    public String testView(@LoginUser User user,
                           @RequestParam("page") String requestPage,
                           Model model)
    {
        Page page = Page.create(requestPage, questionService.provideAllQuestionsCount());

        model.addAttribute("user", user);
        model.addAttribute("questions", questionService.provideQuestions(page));
        model.addAttribute("page", page);

        return "/test";
    }
}
