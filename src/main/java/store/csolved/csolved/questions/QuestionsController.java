package store.csolved.csolved.questions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/questions")
@Controller
public class QuestionsController
{
    @GetMapping("/")
    public String questions()
    {
        return "questions_loby";
    }
}
