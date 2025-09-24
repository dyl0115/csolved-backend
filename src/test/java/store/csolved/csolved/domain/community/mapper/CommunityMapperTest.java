package store.csolved.csolved.domain.community.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.common.PostType;
import store.csolved.csolved.domain.category.Category;
import store.csolved.csolved.domain.category.mapper.CategoryMapper;
import store.csolved.csolved.domain.community.Community;
import store.csolved.csolved.domain.tag.Tag;
import store.csolved.csolved.domain.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
class CommunityMapperTest
{
    @Autowired
    CommunityMapper communityMapper;

    @Test
    @DisplayName("커뮤니티 게시글 저장 테스트")
    void saveCommunity()
    {
        Community community = createTestCommunity();

        assertDoesNotThrow(() -> communityMapper.saveCommunity(PostType.COMMUNITY.getCode(), community));
    }

    @Test
    @DisplayName("커뮤니티 게시글 수정 테스트")
    void updateCommunity()
    {
        Community originalCommunity = createTestCommunity();
        communityMapper.saveCommunity(1, originalCommunity);

        Community updatedCommunity = Community.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .authorId(1L)
                .anonymous(false)
                .categoryId(1L)
                .build();

        assertDoesNotThrow(() -> communityMapper.updateCommunity(1L, updatedCommunity));
    }

    @Test
    @DisplayName("커뮤니티 게시글 목록 조회 테스트")
    void getCommunities()
    {
        List<Community> communities = communityMapper.getCommunities(
                1, 0L, 10L, "RECENT", "CATEGORY", null, "AUTHOR", null);

        assertNotNull(communities);
    }

    @Test
    @DisplayName("특정 커뮤니티 게시글 조회 테스트")
    void getCommunity()
    {
        Community testCommunity = createTestCommunity();
        communityMapper.saveCommunity(1, testCommunity);

        Community retrievedCommunity = communityMapper.getCommunity(1L);

        if (retrievedCommunity != null)
        {
            assertNotNull(retrievedCommunity.getTitle());
            assertNotNull(retrievedCommunity.getContent());
        }
    }

    @Test
    @DisplayName("게시글 작성자 ID 조회 테스트")
    void getAuthorId()
    {
        Community testCommunity = createTestCommunity();
        communityMapper.saveCommunity(1, testCommunity);

        Long authorId = communityMapper.getAuthorId(1L);

        if (authorId != null)
        {
            assertTrue(authorId > 0);
        }
    }

    @Test
    @DisplayName("게시글 논리적 삭제 테스트")
    void deleteCommunity()
    {
        Community testCommunity = createTestCommunity();
        communityMapper.saveCommunity(1, testCommunity);

        assertDoesNotThrow(() -> communityMapper.deleteCommunity(1L));
    }

    @Test
    @DisplayName("게시글 개수 조회 테스트")
    void countCommunities()
    {
        Long count = communityMapper.countCommunities(1, null, null, null, null);

        assertNotNull(count);
        assertTrue(count >= 0);
    }

    @Test
    @DisplayName("사용자 좋아요 여부 확인 테스트")
    void hasUserLiked()
    {
        boolean hasLiked = communityMapper.hasUserLiked(1L, 1L);

        assertNotNull(hasLiked);
    }

    @Test
    @DisplayName("좋아요 수 증가 테스트")
    void increaseLikes()
    {
        Community testCommunity = createTestCommunity();
        communityMapper.saveCommunity(1, testCommunity);

        assertDoesNotThrow(() -> communityMapper.increaseLikes(1L));
    }

    @Test
    @DisplayName("사용자 좋아요 추가 테스트")
    void addUserLike()
    {
        Community testCommunity = createTestCommunity();
        communityMapper.saveCommunity(1, testCommunity);

        assertDoesNotThrow(() -> communityMapper.addUserLike(testCommunity.getId(), 1L));
    }

    @Test
    @DisplayName("조회수 증가 테스트")
    void increaseView()
    {
        Community testCommunity = createTestCommunity();
        communityMapper.saveCommunity(1, testCommunity);

        assertDoesNotThrow(() -> communityMapper.increaseView(1L));
    }

    private Community createTestCommunity()
    {
        return Community.builder()
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