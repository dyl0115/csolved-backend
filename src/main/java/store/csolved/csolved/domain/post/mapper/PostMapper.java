package store.csolved.csolved.domain.post.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import store.csolved.csolved.domain.post.entity.Post;

import java.util.List;

@Mapper
public interface PostMapper
{
    void savePost(Post post);

    void updatePost(@Param("postId") Long postId, @Param("post") Post post);

    // 게시글 개수 조회
    Long countPosts(int postType,
                    String filterType,
                    Long filterValue,
                    String searchType,
                    String searchKeyword);

    // 질문글들 조회
    List<Post> getPosts(int postType,
                        Long offset,
                        Long size,
                        String sortType,
                        String filterType,
                        Long filterValue,
                        String searchType,
                        String searchKeyword);

    // 질문글 조회
    Post getPost(Long postId);

    // 논리적으로 게시글을 삭제
    void deletePost(Long postId);

    void increaseAnswerCount(Long postId);

    void decreaseAnswerCount(Long postId);

    // 질문-좋아요 테이블에 저장된 유저인지 체크
    boolean hasUserLiked(Long postId, Long authorId);

    // 질문 테이블의 Likes 1증가
    void increaseLikes(Long postId);

    // 질문-좋아요 테이블에 questionId, userId 저장 (중복 좋아요 방지)
    void addUserLike(Long postId, Long authorId);

    // 질문 테이블의 Views 1증가
    void increaseView(Long postId);
}
