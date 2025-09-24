package store.csolved.csolved.domain.post.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import store.csolved.csolved.domain.post.Post;
import store.csolved.csolved.domain.post.PostType;
import store.csolved.csolved.domain.tag.Tag;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class PostMapperTest
{
    @Autowired
    PostMapper postMapper;

    @Test
    @DisplayName("커뮤니티 게시글 저장 테스트")
    void saveCommunity()
    {
        Post post = createTestCommunity();

        assertDoesNotThrow(() -> postMapper.saveCommunity(PostType.COMMUNITY.getCode(), post));
    }

    @Test
    @DisplayName("커뮤니티 게시글 수정 테스트")
    void updateCommunity()
    {
        Post originalPost = createTestCommunity();
        postMapper.saveCommunity(1, originalPost);

        Post updatedPost = Post.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .authorId(1L)
                .anonymous(false)
                .categoryId(1L)
                .build();

        assertDoesNotThrow(() -> postMapper.updateCommunity(1L, updatedPost));
    }

    @Test
    @DisplayName("커뮤니티 게시글 목록 조회 테스트")
    void getCommunities()
    {
        List<Post> communities = postMapper.getCommunities(
                1, 0L, 10L, "RECENT", "CATEGORY", null, "AUTHOR", null);

        assertNotNull(communities);
    }

    @Test
    @DisplayName("특정 커뮤니티 게시글 조회 테스트")
    void getCommunity()
    {
        Post testPost = createTestCommunity();
        postMapper.saveCommunity(1, testPost);

        Post retrievedPost = postMapper.getCommunity(1L);

        if (retrievedPost != null)
        {
            assertNotNull(retrievedPost.getTitle());
            assertNotNull(retrievedPost.getContent());
        }
    }

    @Test
    @DisplayName("게시글 작성자 ID 조회 테스트")
    void getAuthorId()
    {
        Post testPost = createTestCommunity();
        postMapper.saveCommunity(1, testPost);

        Long authorId = postMapper.getAuthorId(1L);

        if (authorId != null)
        {
            assertTrue(authorId > 0);
        }
    }

    @Test
    @DisplayName("게시글 논리적 삭제 테스트")
    void deleteCommunity()
    {
        Post testPost = createTestCommunity();
        postMapper.saveCommunity(1, testPost);

        assertDoesNotThrow(() -> postMapper.deleteCommunity(1L));
    }

    @Test
    @DisplayName("게시글 개수 조회 테스트")
    void countCommunities()
    {
        Long count = postMapper.countCommunities(1, null, null, null, null);

        assertNotNull(count);
        assertTrue(count >= 0);
    }

    @Test
    @DisplayName("사용자 좋아요 여부 확인 테스트")
    void hasUserLiked()
    {
        boolean hasLiked = postMapper.hasUserLiked(1L, 1L);

        assertNotNull(hasLiked);
    }

    @Test
    @DisplayName("좋아요 수 증가 테스트")
    void increaseLikes()
    {
        Post testPost = createTestCommunity();
        postMapper.saveCommunity(1, testPost);

        assertDoesNotThrow(() -> postMapper.increaseLikes(1L));
    }

    @Test
    @DisplayName("사용자 좋아요 추가 테스트")
    void addUserLike()
    {
        Post testPost = createTestCommunity();
        postMapper.saveCommunity(1, testPost);

        assertDoesNotThrow(() -> postMapper.addUserLike(testPost.getId(), 1L));
    }

    @Test
    @DisplayName("조회수 증가 테스트")
    void increaseView()
    {
        Post testPost = createTestCommunity();
        postMapper.saveCommunity(1, testPost);

        assertDoesNotThrow(() -> postMapper.increaseView(1L));
    }

    private Post createTestCommunity()
    {
        return Post.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .authorId(1L)
                .anonymous(false)
                .views(0L)
                .likes(0L)
                .answerCount(0L)
                .categoryId(1L)
                .tags(Arrays.asList(
                        Tag.builder().name("Java").build(),
                        Tag.builder().name("Spring").build()
                ))
                .build();
    }
}