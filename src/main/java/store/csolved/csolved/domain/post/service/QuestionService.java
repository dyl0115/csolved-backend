package store.csolved.csolved.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.search.filter.Filtering;
import store.csolved.csolved.domain.search.page.Pagination;
import store.csolved.csolved.domain.search.search.Searching;
import store.csolved.csolved.domain.search.sort.Sorting;
import store.csolved.csolved.domain.post.mapper.PostMapper;
import store.csolved.csolved.domain.post.entity.Post;

import java.util.List;

import static store.csolved.csolved.domain.post.PostType.QUESTION;

@RequiredArgsConstructor
@Service
public class QuestionService
{
    private final PostMapper postMapper;

    public Long countQuestions(Filtering filter, Searching search)
    {
        return postMapper.countPosts(
                QUESTION.getCode(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
    }

    public Post getQuestion(Long questionId)
    {
        return postMapper.getPost(questionId);
    }

    public List<Post> getQuestions(Pagination page,
                                   Sorting sort,
                                   Filtering filter,
                                   Searching search)
    {
        return postMapper.getPosts(
                QUESTION.getCode(),
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
    public Post viewQuestion(Long questionId)
    {
        postMapper.increaseView(questionId);
        return postMapper.getPost(questionId);
    }

    @Transactional
    public Long save(Post question)
    {
        postMapper.save(question);
        return question.getId();
    }

    @Transactional
    public Long update(Long questionId, Post question)
    {
        postMapper.update(questionId, question);
        return questionId;
    }

    @Transactional
    public void delete(Long questionId)
    {
        postMapper.softDelete(questionId);
    }

    @Transactional
    public boolean addLike(Long questionId, Long userId)
    {
        if (postMapper.hasUserLiked(questionId, userId))
        {
            return false;
        }

        postMapper.addUserLike(questionId, userId);
        postMapper.increaseLikes(questionId);
        return true;
    }
}