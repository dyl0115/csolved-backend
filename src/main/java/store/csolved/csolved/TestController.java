package store.csolved.csolved;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import store.csolved.csolved.domain.answer.entity.Answer;
import store.csolved.csolved.domain.answer.service.AnswerService;
import store.csolved.csolved.domain.comment.entity.Comment;
import store.csolved.csolved.domain.comment.service.CommentService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TestController
{
    private final AnswerService answerService;
    private final CommentService commentService;

    @GetMapping("/test")
    public Map<Long, List<Comment>> testView()
    {
        List<Answer> answers = answerService.getAnswers(2L);
        return commentService.getComments(getIds(answers));
    }

    private List<Long> getIds(List<Answer> answers)
    {
        return answers.stream()
                .map(Answer::getId)
                .toList();
    }
}
