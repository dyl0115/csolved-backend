package store.csolved.csolved.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.domain.post.mapper.record.PostCardRecord;
import store.csolved.csolved.domain.post.controller.response.PostDetailResponse;
import store.csolved.csolved.domain.post.controller.response.PostListResponse;
import store.csolved.csolved.global.exception.CsolvedException;
import store.csolved.csolved.global.exception.ExceptionCode;
import store.csolved.csolved.domain.post.mapper.PostMapper;
import store.csolved.csolved.domain.post.mapper.record.PostDetailRecord;
import store.csolved.csolved.domain.post.service.result.RepliedPostsAndPageResult;
import store.csolved.csolved.domain.post.service.result.UserPostsAndPageResult;
import store.csolved.csolved.global.utils.filter.Filtering;
import store.csolved.csolved.global.utils.page.Pagination;
import store.csolved.csolved.global.utils.page.PaginationManager;
import store.csolved.csolved.global.utils.search.Searching;
import store.csolved.csolved.global.utils.sort.Sorting;

import java.util.List;

import static store.csolved.csolved.domain.post.mapper.entity.PostType.COMMUNITY;

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
                search.getSearchKeyword());

        // 사용자가 요청한 페이지 번호, 글 개수를 사용하여 페이지 정보를 생성
        Pagination pagination = paginationManager.createPagination(pageNumber, totalPosts);

        // 페이지 정보를 사용하여 DB에 필요한 커뮤니티글만 조회
        List<PostCardRecord> posts = postMapper.getPosts(COMMUNITY.getCode(),
                pagination.getOffset(),
                pagination.getSize(),
                sort.name(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getSearchKeyword());

        return PostListResponse.from(pagination, posts);
    }


    // 커뮤니티글 상세 조회
    public PostDetailResponse getPost(Long postId)
    {
        PostDetailRecord post = postMapper.getPost(postId);

        if (post == null)
        {
            throw new CsolvedException(ExceptionCode.POST_NOT_FOUND);
        }

        postMapper.increaseView(postId);

        return PostDetailResponse.from(post);
    }

    // 댓글을 작성한 글 조회
    public RepliedPostsAndPageResult getRepliedPostsAndPage(Long userId,
                                                            Long pageNumber)
    {
        // DB에서 회원의 댓글과 대댓글과 관련된 게시글들의 수를 가져옴.
        Long totalPosts = postMapper.countRepliedPosts(userId);

        // 가져온 게시글들의 개수를 사용하여 페이지 정보를 생성
        Pagination page = paginationManager.createPagination(pageNumber, totalPosts);

        // 페이지 정보를 사용하여 회원의 댓글과 대댓글과 관련된 게시글들을 조회
        List<PostCardRecord> posts = postMapper.getRepliedPosts(userId, page);

        return RepliedPostsAndPageResult.from(totalPosts, posts, page);
    }

    // 내가 작성한 글 조회
    public UserPostsAndPageResult getUserPostsAndPage(Long userId,
                                                      Long pageNumber)
    {
        // DB에서 회원이 작성한 게시글의 수를 가져옴.
        Long totalPosts = postMapper.countUserPosts(userId);

        // 가져온 게시글들의 개수를 사용하여 페이지 정보를 생성
        Pagination page = paginationManager.createPagination(pageNumber, totalPosts);

        // 페이지 정보를 사용하여 회원의 게시글들을 조회
        List<PostCardRecord> posts = postMapper.getUserPosts(userId, page);

        return UserPostsAndPageResult.from(totalPosts, posts, page);
    }
}
