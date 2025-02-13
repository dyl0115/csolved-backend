package store.csolved.csolved.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.question.Question;
import store.csolved.csolved.domain.question.mapper.QuestionMapper;
import store.csolved.csolved.utils.filter.Filtering;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.search.Searching;
import store.csolved.csolved.utils.sort.Sorting;

import java.util.List;

import static store.csolved.csolved.common.PostType.QUESTION;

@RequiredArgsConstructor
@Service
public class QuestionService
{
    private final QuestionMapper questionMapper;

    @Transactional
    public Long save(Question question)
    {
        questionMapper.saveQuestion(QUESTION.getCode(), question);
        return question.getId();
    }

    public Long countQuestions(Filtering filter, Searching search)
    {
        return questionMapper.countQuestions(
                QUESTION.getCode(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
    }

    public Question getQuestion(Long questionId)
    {
        return questionMapper.getQuestion(questionId);
    }

    public List<Question> getPosts(Pagination page,
                                   Sorting sort,
                                   Filtering filter,
                                   Searching search)
    {
        return questionMapper.getQuestions(
                QUESTION.getCode(),
                page.getOffset(),
                page.getSize(),
                sort.name(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
    }

    @Transactional
    public Question viewPost(Long questionId)
    {
        questionMapper.increaseView(questionId);
        return questionMapper.getQuestion(questionId);
    }

    @Transactional
    public Long update(Long questionId, Question question)
    {
        questionMapper.updateQuestion(questionId, question);
        return questionId;
    }

    @Transactional
    public void delete(Long questionId)
    {
        questionMapper.deleteQuestion(questionId);
    }

    @Transactional
    public boolean addLike(Long questionId, Long userId)
    {
        if (questionMapper.hasUserLiked(questionId, userId))
        {
            return false;
        }

        questionMapper.addUserLike(questionId, userId);
        questionMapper.increaseLikes(questionId);
        return true;
    }
}