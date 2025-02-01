package store.csolved.csolved.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.search.filter.Filtering;
import store.csolved.csolved.domain.search.page.Pagination;
import store.csolved.csolved.domain.search.search.Searching;
import store.csolved.csolved.domain.search.sort.Sorting;
import store.csolved.csolved.domain.post.mapper.QuestionMapper;
import store.csolved.csolved.domain.post.entity.Question;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService
{
    private final QuestionMapper questionMapper;

    public Long countQuestions(Filtering filter, Searching search)
    {
        return questionMapper.countQuestions(
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
    }

    public Question getQuestion(Long questionId)
    {
        return questionMapper.getQuestion(questionId);
    }

    public List<Question> getQuestions(Pagination page,
                                       Sorting sort,
                                       Filtering filter,
                                       Searching search)
    {
        return questionMapper.getQuestions(
                page.getOffset(),
                page.getSize(),
                sort.name(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
    }

    // 질문글의 조회수를 1만큼 올리고, 질문 상세를 보여줌.
    @Transactional
    public Question viewQuestion(Long questionId)
    {
        questionMapper.increaseView(questionId);
        return questionMapper.getQuestion(questionId);
    }

    @Transactional
    public Long save(Question question)
    {
        questionMapper.save(question);
        return question.getId();
    }

    @Transactional
    public Long update(Long questionId, Question question)
    {
        questionMapper.update(questionId, question);
        return questionId;
    }

    @Transactional
    public void delete(Long questionId)
    {
        questionMapper.softDelete(questionId);
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
