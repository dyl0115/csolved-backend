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
        postMapper.savePost(post);
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
        List<Post> posts = postMapper.getPosts(
                postTypeCode,
                page.getOffset(),
                page.getSize(),
                sort.name(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
        return posts;
    }

    // 질문글의 조회수를 1만큼 올리고, 질문 상세를 보여줌.
    @Transactional
    public Post viewPost(Long postId)
    {
        postMapper.increaseView(postId);

        Post post = postMapper.getPost(postId);

//        System.out.println("PostService.getPost()");
//        System.out.println("Post.title: " + post.getTitle());
//        post.getTags().forEach(tag ->
//        {
//            System.out.println("    id:" + tag.getId());
//            System.out.println("    name:" + tag.getName());
//        });
        return post;
    }

    @Transactional
    public Long update(Long postId, Post post)
    {
        postMapper.updatePost(postId, post);
        return postId;
    }

    @Transactional
    public void delete(Long postId)
    {
        postMapper.deletePost(postId);
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