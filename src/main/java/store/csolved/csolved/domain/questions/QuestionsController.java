package store.csolved.csolved.domain.questions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.csolved.csolved.config.auth.LoginUser;
import store.csolved.csolved.domain.user.User;

@RequestMapping("/questions")
@Controller
public class QuestionsController
{
    @GetMapping("")
    public String questions(@LoginUser User user)
    {
        System.out.println("id" + user.getId());
        return "questions_loby";
    }
}
