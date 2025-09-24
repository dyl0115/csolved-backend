package store.csolved.csolved.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.domain.post.mapper.entity.Post;
import store.csolved.csolved.domain.post.mapper.record.PostCard;
import store.csolved.csolved.domain.post.controller.dto.response.PostDetailResponse;
import store.csolved.csolved.domain.post.controller.dto.response.PostListResponse;
import store.csolved.csolved.domain.post.exception.PostNotFoundException;
import store.csolved.csolved.domain.post.mapper.PostMapper;
import store.csolved.csolved.domain.post.mapper.record.PostDetail;
import store.csolved.csolved.utils.filter.Filtering;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.page.PaginationManager;
import store.csolved.csolved.utils.search.Searching;
import store.csolved.csolved.utils.sort.Sorting;

import java.util.List;

import static store.csolved.csolved.domain.post.PostType.COMMUNITY;

@RequiredArgsConstructor
@Service
public class PostQueryService
{
    private final PaginationManager paginationManager;
    private final PostMapper postMapper;

    // 커뮤니티글 리스트 조회
    public PostListResponse getPosts(Long pageNumber,
                                     Sorting sort,
                                     Filtering filter,
                                     Searching search)
    {
        // DB에서 커뮤니티글 개수를 가져옴
        Long totalPosts = postMapper.countPosts(
                COMMUNITY.getCode(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());

        // 사용자가 요청한 페이지 번호, 글 개수를 사용하여 페이지 정보를 생성
        Pagination pagination = paginationManager.createPagination(pageNumber, totalPosts);

        // 페이지 정보를 사용하여 DB에 필요한 커뮤니티글만 조회
        List<PostCard> posts = postMapper.getPosts(COMMUNITY.getCode(),
                pagination.getOffset(),
                pagination.getSize(),
                sort.name(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());

        return PostListResponse.from(pagination, posts);
    }


    // 커뮤니티글 상세 조회
    public PostDetailResponse getCommunityPost(Long postId)
    {
        PostDetail post = postMapper.getPost(postId);

        if (post == null)
        {
            throw new PostNotFoundException();
        }

        postMapper.increaseView(postId);

        return PostDetailResponse.from(post);
    }

    // 내가 댓글 단 게시글 리스트 조회
    public List<PostCard> getRepliedPosts(Long userId, Pagination page)
    {
        return postMapper.getRepliedPosts(userId, page);
    }

    // 내가 댓글 단 게시글 개수 조회
    public Long countRepliedPosts(Long userId)
    {
        return postMapper.countRepliedPosts(userId);
    }

    // 내가 작성한 게시글 조회
    public List<PostCard> getUserPosts(Long userId, Pagination page)
    {
        return postMapper.getUserPosts(userId, page);
    }

    // 내가 작성한 게시글 개수 조회
    public Long countUserPosts(Long userId)
    {
        return postMapper.countUserPosts(userId);
    }
}
