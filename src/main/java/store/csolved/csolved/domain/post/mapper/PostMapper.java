package store.csolved.csolved.domain.post.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import store.csolved.csolved.domain.post.Post;
import store.csolved.csolved.domain.post.PostCard;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Mapper
public interface PostMapper
{
    void saveCommunity(int postType, Post post);

    void updateCommunity(Long communityId, Post post);

    // 질문글들 조회
    List<Post> getCommunities(int postType,
                              Long offset,
                              Long size,
                              String sortType,
                              String filterType,
                              Long filterValue,
                              String searchType,
                              String searchKeyword);

    // 질문글 조회
    Post getCommunity(Long communityId);

    // 게시글 작성자 조회
    Long getAuthorId(Long communityId);

    // 논리적으로 게시글을 삭제
    void deleteCommunity(Long communityId);

    // 게시글 개수 조회
    Long countCommunities(int postType,
                          String filterType,
                          Long filterValue,
                          String searchType,
                          String searchKeyword);

    // 좋아요 테이블에 저장된 유저인지 체크
    boolean hasUserLiked(Long communityId, Long authorId);

    // 테이블의 Likes 1증가
    void increaseLikes(Long communityId);

    // 좋아요 테이블에 questionId, userId 저장 (중복 좋아요 방지)
    void addUserLike(Long communityId, Long authorId);

    // 테이블의 Views 1증가
    void increaseView(Long communityId);

    // 댓글 단 게시글 카드 조회
    List<PostCard> getRepliedPosts(@Param("userId") Long userId,
                                   @Param("page") Pagination page);

    // 댓글 단 게시글 개수 조회
    Long countRepliedPosts(Long userId);

    // 내가 쓴 게시글 카드 조회
    List<PostCard> getUserPosts(Long userId, Pagination page);

    // 내가 쓴 게시글 개수 조회
    Long countUserPosts(Long userId);

}
