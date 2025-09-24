package store.csolved.csolved.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.domain.post.mapper.record.PostCard;
import store.csolved.csolved.domain.bookmark.mapper.BookmarkMapper;
import store.csolved.csolved.domain.post.mapper.PostMapper;
import store.csolved.csolved.domain.user.service.dto.result.BookmarksAndPageResult;
import store.csolved.csolved.domain.user.service.dto.result.RepliedPostsAndPageResult;
import store.csolved.csolved.domain.user.service.dto.result.UserPostsAndPageResult;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.page.PaginationManager;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserActivityService
{
    private final BookmarkMapper bookmarkMapper;
    private final PostMapper postMapper;
    private final PaginationManager paginationManager;

    public BookmarksAndPageResult getBookmarksAndPage(Long userId,
                                                      Long pageNumber)
    {
        // DB에서 북마크 개수를 가져옴.
        Long totalPosts = bookmarkMapper.countBookmarks(userId);

        // 사용자가 요청한 페이지 번호, 북마크 개수를 사용하여 페이지 정보를 생성
        Pagination bookmarksPage = paginationManager.createPagination(pageNumber, totalPosts);

        // 페이지 정보를 사용하여 DB에서 필요한 북마크만 조회.
        List<PostCard> posts = bookmarkMapper.getBookmarks(userId, bookmarksPage);

        return BookmarksAndPageResult.from(totalPosts, posts, bookmarksPage);
    }

    public RepliedPostsAndPageResult getRepliedPostsAndPage(Long userId,
                                                            Long pageNumber)
    {
        // DB에서 회원의 댓글과 대댓글과 관련된 게시글들의 수를 가져옴.
        Long totalPosts = postMapper.countRepliedPosts(userId);

        // 가져온 게시글들의 개수를 사용하여 페이지 정보를 생성
        Pagination page = paginationManager.createPagination(pageNumber, totalPosts);

        // 페이지 정보를 사용하여 회원의 댓글과 대댓글과 관련된 게시글들을 조회
        List<PostCard> posts = postMapper.getRepliedPosts(userId, page);

        return RepliedPostsAndPageResult.from(totalPosts, posts, page);
    }

    public UserPostsAndPageResult getUserPostsAndPage(Long userId,
                                                      Long pageNumber)
    {
        // DB에서 회원이 작성한 게시글의 수를 가져옴.
        Long totalPosts = postMapper.countUserPosts(userId);

        // 가져온 게시글들의 개수를 사용하여 페이지 정보를 생성
        Pagination page = paginationManager.createPagination(pageNumber, totalPosts);

        // 페이지 정보를 사용하여 회원의 게시글들을 조회
        List<PostCard> posts = postMapper.getUserPosts(userId, page);

        return UserPostsAndPageResult.from(totalPosts, posts, page);
    }
}
