package store.csolved.csolved.domain.post.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import store.csolved.csolved.domain.post.mapper.entity.Post;
import store.csolved.csolved.domain.post.mapper.record.PostCardRecord;
import store.csolved.csolved.domain.post.mapper.record.PostDetailRecord;
import store.csolved.csolved.utils.page.Pagination;

import java.util.List;

@Mapper
public interface PostMapper
{
    void savePost(int postType, Post post);

    void updatePost(Long communityId, Post post);

    // 질문글들 조회
    List<PostCardRecord> getPosts(int postType,
                                  Long offset,
                                  Long size,
                                  String sortType,
                                  String filterType,
                                  Long filterValue,
                                  String searchType,
                                  String searchKeyword);

    // 질문글 조회
    PostDetailRecord getPost(Long postId);

    // 게시글 작성자 조회
    Long getAuthorId(Long communityId);

    // 논리적으로 게시글을 삭제
    void deletePost(Long communityId);

    // 게시글 개수 조회
    Long countPosts(int postType,
                    String filterType,
                    Long filterValue,
                    String searchType,
                    String searchKeyword);

    // 좋아요 테이블에 저장된 유저인지 체크
    boolean hasUserLiked(Long communityId, Long authorId);

    // 테이블의 Likes 1증가
    void increaseLikes(Long communityId);

    // 좋아요 테이블에 postId, userId 저장 (중복 좋아요 방지)
    void addUserLike(Long communityId, Long authorId);

    // 테이블의 Views 1증가
    void increaseView(Long communityId);

    // 댓글 단 게시글 카드 조회
    List<PostCardRecord> getRepliedPosts(@Param("userId") Long userId,
                                         @Param("page") Pagination page);

    // 댓글 단 게시글 개수 조회
    Long countRepliedPosts(Long userId);

    // 내가 쓴 게시글 카드 조회
    List<PostCardRecord> getUserPosts(Long userId, Pagination page);

    // 내가 쓴 게시글 개수 조회
    Long countUserPosts(Long userId);

}
