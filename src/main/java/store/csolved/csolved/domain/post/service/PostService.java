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

@RequiredArgsConstructor
@Service
public class PostService
{
    private final PostMapper postMapper;

    @Transactional
    public Long save(Post post)
    {
        postMapper.save(post);
        return post.getId();
    }

    public Long countPosts(int postTypeCode, Filtering filter, Searching search)
    {
        return postMapper.countPosts(
                postTypeCode,
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
    }

    public Post getPost(Long postId)
    {
        return postMapper.getPost(postId);
    }

    public List<Post> getPosts(int postTypeCode,
                               Pagination page,
                               Sorting sort,
                               Filtering filter,
                               Searching search)
    {
        return postMapper.getPosts(
                postTypeCode,
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
    public Post viewPost(Long postId)
    {
        postMapper.increaseView(postId);
        return postMapper.getPost(postId);
    }

    @Transactional
    public Long update(Long postId, Post post)
    {
        postMapper.update(postId, post);
        return postId;
    }

    @Transactional
    public void delete(Long postId)
    {
        postMapper.softDelete(postId);
    }

    @Transactional
    public boolean addLike(Long postId, Long userId)
    {
        if (postMapper.hasUserLiked(postId, userId))
        {
            return false;
        }

        postMapper.addUserLike(postId, userId);
        postMapper.increaseLikes(postId);
        return true;
    }
}