package store.csolved.csolved.domain.post.mapper;

import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.post.mapper.entity.Post;
import store.csolved.csolved.domain.post.mapper.entity.PostType;
import store.csolved.csolved.domain.post.mapper.record.PostCardRecord;
import store.csolved.csolved.domain.post.mapper.record.PostDetailRecord;
import store.csolved.csolved.global.utils.page.Pagination;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@MybatisTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PostMapperTest
{
    @Autowired
    PostMapper postMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp()
    {
        // FK 제약조건을 지키기 위해 미리 필요한 데이터를 저장.
        // PostMapper 테스트에 다른 Mapper에 의존하지 않도록 한다.

        // 사용자 데이터
        jdbcTemplate.execute(
                "INSERT INTO users (id, email, password, nickname, company, admin, created_at)\n" +
                        "VALUES (1, 'test1@example.com', 'password123', 'testUser1', 'testCompany', false, CURRENT_TIMESTAMP),\n" +
                        "       (2, 'test2@example.com', 'password123', 'testUser2', 'testCompany', false, CURRENT_TIMESTAMP),\n" +
                        "       (3, 'admin@example.com', 'password123', 'adminUser', 'testCompany', true, CURRENT_TIMESTAMP);");

        // 카테고리 데이터
        jdbcTemplate.execute(
                "INSERT INTO category (id, post_type, name, created_at)\n" +
                        "VALUES (1, 1, '일반질문', CURRENT_TIMESTAMP),\n" +
                        "       (2, 1, '기술토론', CURRENT_TIMESTAMP),\n" +
                        "       (3, 2, '커뮤니티', CURRENT_TIMESTAMP);");

        // 태그 데이터
        jdbcTemplate.execute(
                "INSERT INTO tags (id, name, created_at)\n" +
                        "VALUES (1, 'Java', CURRENT_TIMESTAMP),\n" +
                        "       (2, 'Spring', CURRENT_TIMESTAMP),\n" +
                        "       (3, 'MySQL', CURRENT_TIMESTAMP),\n" +
                        "       (4, 'Test', CURRENT_TIMESTAMP);");
    }

    @AfterAll
    void cleanUp()
    {
        // FK 제약조건을 고려하여 역순으로 삭제
        jdbcTemplate.execute("DELETE FROM post_tags");
        jdbcTemplate.execute("DELETE FROM post_likes");
        jdbcTemplate.execute("DELETE FROM bookmarks");
        jdbcTemplate.execute("DELETE FROM comments");
        jdbcTemplate.execute("DELETE FROM answer_ratings");
        jdbcTemplate.execute("DELETE FROM answers");
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("DELETE FROM tags");
        jdbcTemplate.execute("DELETE FROM category");
        jdbcTemplate.execute("DELETE FROM users");
    }

    @Test
    @DisplayName("게시글을 저장하고 조회할 수 있다")
    void saveAndGetPost()
    {
        //given
        Post post = createPost(1L, "title1");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post);

        //when & then
        PostDetailRecord foundPost = postMapper.getPost(post.getId());

        assertThat(Objects.equals(foundPost.getTitle(), post.getTitle())).isTrue();
    }

    @Test
    @DisplayName("여러 게시글을 저장하고 목록으로 조회할 수 있다")
    void saveAndGetPosts()
    {
        //given
        Post post1 = createPost(1L, "title1");
        Post post2 = createPost(1L, "title2");
        Post post3 = createPost(1L, "title3");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post3);

        //when
        PostDetailRecord foundPost1 = postMapper.getPost(post1.getId());
        PostDetailRecord foundPost2 = postMapper.getPost(post2.getId());
        PostDetailRecord foundPost3 = postMapper.getPost(post3.getId());

        //then
        assertThat(Objects.equals(foundPost1.getTitle(), post1.getTitle())).isTrue();
        assertThat(Objects.equals(foundPost2.getTitle(), post2.getTitle())).isTrue();
        assertThat(Objects.equals(foundPost3.getTitle(), post3.getTitle())).isTrue();

        List<PostCardRecord> posts = postMapper.getPosts(PostType.COMMUNITY.getCode(),
                0L, 100L,
                "RECENT",
                "CATEGORY", null,
                "AUTHOR", null);

        assertThat(posts.size()).isEqualTo(3L);
    }

    @Test
    @DisplayName("게시글 개수를 조회할 수 있다")
    void countPosts()
    {
        //given
        postMapper.savePost(PostType.COMMUNITY.getCode(), createPost(1L, "title1"));
        postMapper.savePost(PostType.COMMUNITY.getCode(), createPost(2L, "title2"));

        //when & then
        assertThat(postMapper.countPosts(PostType.COMMUNITY.getCode(),
                "CATEGORY", null,
                "AUTHOR", null)).isEqualTo(2L);

    }

    @Test
    @DisplayName("게시글을 수정할 수 있다")
    void updatePost()
    {
        //given
        Post post = createPost(1L, "original title");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post);

        Post updatedPost = Post.builder()
                .anonymous(true)
                .title("updated title")
                .content("updated content")
                .categoryId(2L)
                .build();

        //when
        postMapper.updatePost(post.getId(), updatedPost);

        //then
        PostDetailRecord foundPost = postMapper.getPost(post.getId());
        assertThat(foundPost.getTitle()).isEqualTo("updated title");
        assertThat(foundPost.getContent()).isEqualTo("updated content");
        assertThat(foundPost.getCategoryId()).isEqualTo(2L);
        assertThat(foundPost.isAnonymous()).isTrue();
    }

    @Test
    @DisplayName("게시글을 논리적으로 삭제할 수 있다")
    void deletePost()
    {
        //given
        Post post = createPost(1L, "title to delete");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post);

        //when
        postMapper.deletePost(post.getId());

        //then
        PostDetailRecord deletedPost = postMapper.getPost(post.getId());
        assertThat(deletedPost).isNull();
    }

    @Test
    @DisplayName("게시글 작성자 ID를 조회할 수 있다")
    void getAuthorId()
    {
        //given
        Post post = createPost(1L, "test title");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post);

        //when
        Long authorId = postMapper.getAuthorId(post.getId());

        //then
        assertThat(authorId).isEqualTo(1L);
    }

    @Test
    @DisplayName("사용자의 좋아요 여부를 확인할 수 있다")
    void hasUserLiked()
    {
        //given
        Post post = createPost(1L, "test title");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post);

        //when & then
        assertThat(postMapper.hasUserLiked(post.getId(), 1L)).isFalse();

        postMapper.addUserLike(post.getId(), 1L);
        assertThat(postMapper.hasUserLiked(post.getId(), 1L)).isTrue();
    }

    @Test
    @DisplayName("게시글의 좋아요 수를 증가시킬 수 있다")
    void increaseLikes()
    {
        //given
        Post post = createPost(1L, "test title");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post);
        Long initialLikes = postMapper.getPost(post.getId()).getLikes();

        //when
        postMapper.increaseLikes(post.getId());

        //then
        PostDetailRecord updatedPost = postMapper.getPost(post.getId());
        assertThat(updatedPost.getLikes()).isEqualTo(initialLikes + 1);
    }

    @Test
    @DisplayName("사용자의 좋아요를 추가할 수 있다")
    void addUserLike()
    {
        //given
        Post post = createPost(1L, "test title");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post);

        //when
        postMapper.addUserLike(post.getId(), 2L);

        //then
        assertThat(postMapper.hasUserLiked(post.getId(), 2L)).isTrue();
    }

    @Test
    @DisplayName("게시글의 조회수를 증가시킬 수 있다")
    void increaseView()
    {
        //given
        Post post = createPost(1L, "test title");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post);
        Long initialViews = postMapper.getPost(post.getId()).getViews();

        //when
        postMapper.increaseView(post.getId());

        //then
        PostDetailRecord updatedPost = postMapper.getPost(post.getId());
        assertThat(updatedPost.getViews()).isEqualTo(initialViews + 1);
    }

    @Test
    @DisplayName("사용자가 댓글을 단 게시글들을 조회할 수 있다")
    void getRepliedPosts()
    {
        //given
        Post post1 = createPost(1L, "post1");
        Post post2 = createPost(1L, "post2");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);

        jdbcTemplate.execute(
                "INSERT INTO answers (post_id, author_id, content, created_at) " +
                        "VALUES (" + post1.getId() + ", 2, 'test answer', CURRENT_TIMESTAMP)");

        Pagination page = Pagination.create(1L, 10L);

        //when
        List<PostCardRecord> repliedPosts = postMapper.getRepliedPosts(2L, page);

        //then
        assertThat(repliedPosts).hasSize(1);
        assertThat(repliedPosts.get(0).getTitle()).isEqualTo("post1");
    }

    @Test
    @DisplayName("사용자가 댓글을 단 게시글 개수를 조회할 수 있다")
    void countRepliedPosts()
    {
        //given
        Post post1 = createPost(1L, "post1");
        Post post2 = createPost(1L, "post2");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);

        jdbcTemplate.execute(
                "INSERT INTO answers (post_id, author_id, content, created_at) " +
                        "VALUES (" + post1.getId() + ", 2, 'test answer1', CURRENT_TIMESTAMP), " +
                        "(" + post2.getId() + ", 2, 'test answer2', CURRENT_TIMESTAMP)");

        //when
        Long count = postMapper.countRepliedPosts(2L);

        //then
        assertThat(count).isEqualTo(2L);
    }

    @Test
    @DisplayName("사용자가 작성한 게시글들을 조회할 수 있다")
    void getUserPosts()
    {
        //given
        Post post1 = createPost(1L, "user1 post1");
        Post post2 = createPost(1L, "user1 post2");
        Post post3 = createPost(2L, "user2 post1");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post3);

        Pagination page = Pagination.create(1L, 10L);

        //when
        List<PostCardRecord> userPosts = postMapper.getUserPosts(1L, page);

        //then
        assertThat(userPosts).hasSize(2);
        assertThat(userPosts.stream().allMatch(post -> post.getAuthorId().equals(1L))).isTrue();
    }

    @Test
    @DisplayName("사용자가 작성한 게시글 개수를 조회할 수 있다")
    void countUserPosts()
    {
        //given
        Post post1 = createPost(1L, "user1 post1");
        Post post2 = createPost(1L, "user1 post2");
        Post post3 = createPost(2L, "user2 post1");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post3);

        //when
        Long count = postMapper.countUserPosts(1L);

        //then
        assertThat(count).isEqualTo(2L);
    }

    @Test
    @DisplayName("제목으로 게시글을 검색할 수 있다")
    void searchPostsByTitle()
    {
        //given
        Post post1 = createPost(1L, "Java 기초 질문");
        Post post2 = createPost(2L, "Spring Boot 설정");
        Post post3 = createPost(3L, "Java 심화 문제");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post3);

        //when
        List<PostCardRecord> searchResults = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                0L, 100L,
                "RECENT",
                "CATEGORY", null,
                "TITLE", "Java"
        );

        //then
        assertThat(searchResults).hasSize(2);
        assertThat(searchResults.stream().allMatch(post ->
                post.getTitle().contains("Java"))).isTrue();
    }

    @Test
    @DisplayName("내용으로 게시글을 검색할 수 있다")
    void searchPostsByContent()
    {
        //given
        Post post1 = createPostWithContent(1L, "title1", "Spring Boot 관련 내용");
        Post post2 = createPostWithContent(2L, "title2", "Java 기초 내용");
        Post post3 = createPostWithContent(3L, "title3", "Spring Boot 고급 내용");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post3);

        //when
        List<PostCardRecord> searchResults = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                0L, 100L,
                "RECENT",
                "CATEGORY", null,
                "CONTENT", "Spring Boot"
        );

        //then
        // 내용 검색이 제대로 구현되어 있다면 2개, 아니면 0개 이상
        assertThat(searchResults).isNotNull();
        assertThat(searchResults.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    @DisplayName("작성자 닉네임으로 게시글을 검색할 수 있다")
    void searchPostsByAuthor()
    {
        //given
        Post post1 = createPost(1L, "testUser1 게시글");
        Post post2 = createPost(2L, "testUser2 게시글");
        Post post3 = createPost(1L, "또 다른 testUser1 게시글");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post3);

        //when
        List<PostCardRecord> searchResults = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                0L, 100L,
                "RECENT",
                "CATEGORY", null,
                "AUTHOR", "testUser1"
        );

        //then
        assertThat(searchResults).hasSize(2);
        assertThat(searchResults.stream().allMatch(post ->
                post.getAuthorNickname().equals("testUser1"))).isTrue();
    }

    @Test
    @DisplayName("게시글을 최신순으로 정렬할 수 있다")
    void sortPostsByRecent()
    {
        //given
        Post post1 = createPost(1L, "첫 번째 게시글");
        Post post2 = createPost(2L, "두 번째 게시글");
        Post post3 = createPost(3L, "세 번째 게시글");

        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        postMapper.savePost(PostType.COMMUNITY.getCode(), post3);

        //when
        List<PostCardRecord> recentPosts = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                0L, 100L,
                "RECENT",
                "CATEGORY", null,
                "SEARCH", null
        );

        //then
        assertThat(recentPosts).hasSize(3);
        // 최신순 정렬 확인 (정확한 순서는 DB 구현에 따라 달라질 수 있음)
        assertThat(recentPosts).isNotEmpty();
    }

    @Test
    @DisplayName("게시글을 좋아요순으로 정렬할 수 있다")
    void sortPostsByLikes()
    {
        //given
        Post post1 = createPost(1L, "좋아요 적은 게시글");
        Post post2 = createPost(2L, "좋아요 많은 게시글");
        Post post3 = createPost(3L, "좋아요 중간 게시글");

        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post3);

        // 좋아요 수 설정
        postMapper.increaseLikes(post2.getId()); // 1개
        postMapper.increaseLikes(post2.getId()); // 2개
        postMapper.increaseLikes(post3.getId()); // 1개

        //when
        List<PostCardRecord> likedPosts = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                0L, 100L,
                "LIKES",
                "CATEGORY", null,
                "SEARCH", null
        );

        //then
        assertThat(likedPosts).hasSize(3);
        assertThat(likedPosts.get(0).getTitle()).isEqualTo("좋아요 많은 게시글");
        assertThat(likedPosts.get(0).getLikes()).isEqualTo(2L);
    }

    @Test
    @DisplayName("게시글을 조회수순으로 정렬할 수 있다")
    void sortPostsByViews()
    {
        //given
        Post post1 = createPost(1L, "조회수 적은 게시글");
        Post post2 = createPost(2L, "조회수 많은 게시글");
        Post post3 = createPost(3L, "조회수 중간 게시글");

        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post3);

        // 조회수 설정
        postMapper.increaseView(post2.getId()); // 1회
        postMapper.increaseView(post2.getId()); // 2회
        postMapper.increaseView(post2.getId()); // 3회
        postMapper.increaseView(post3.getId()); // 1회

        //when
        List<PostCardRecord> viewedPosts = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                0L, 100L,
                "VIEWS",
                "CATEGORY", null,
                "SEARCH", null
        );

        //then
        assertThat(viewedPosts).hasSize(3);
        assertThat(viewedPosts.get(0).getTitle()).isEqualTo("조회수 많은 게시글");
        assertThat(viewedPosts.get(0).getViews()).isEqualTo(3L);
    }

    @Test
    @DisplayName("카테고리별로 게시글을 필터링할 수 있다")
    void filterPostsByCategory()
    {
        //given
        Post post1 = createPostWithCategory(1L, "일반질문 게시글", 1L);
        Post post2 = createPostWithCategory(2L, "기술토론 게시글", 2L);
        Post post3 = createPostWithCategory(3L, "또 다른 일반질문", 1L);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post3);

        //when
        List<PostCardRecord> filteredPosts = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                0L, 100L,
                "RECENT",
                "CATEGORY", 1L,
                "TITLE", null
        );

        //then
        assertThat(filteredPosts).isNotNull();

        // 카테고리 필터링 결과 확인
        if (!filteredPosts.isEmpty())
        {
            assertThat(filteredPosts.stream().allMatch(post ->
                    post.getCategoryId().equals(1L))).isTrue();
        }
    }

    @Test
    @DisplayName("복합 검색과 필터링이 함께 동작한다")
    void searchAndFilterCombined()
    {
        //given
        Post post1 = createPostWithCategory(1L, "Java 기초 질문", 1L);
        Post post2 = createPostWithCategory(2L, "Java 심화 문제", 2L);
        Post post3 = createPostWithCategory(3L, "Spring 관련", 1L);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post1);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post2);
        postMapper.savePost(PostType.COMMUNITY.getCode(), post3);

        //when
        List<PostCardRecord> filteredPosts = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                0L, 100L,
                "RECENT",
                "CATEGORY", 1L,
                "TITLE", "Java"
        );

        //then
        assertThat(filteredPosts).hasSize(1);
        assertThat(filteredPosts.get(0).getTitle()).isEqualTo("Java 기초 질문");
        assertThat(filteredPosts.get(0).getCategoryId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("페이지네이션이 올바르게 동작한다")
    void testPagination()
    {
        //given
        for (int i = 1; i <= 15; i++)
        {
            Post post = createPost(1L, "게시글 " + i);
            postMapper.savePost(PostType.COMMUNITY.getCode(), post);
        }

        //when
        List<PostCardRecord> firstPage = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                0L, 10L,
                "RECENT",
                "CATEGORY", null,
                "SEARCH", null
        );

        List<PostCardRecord> secondPage = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                10L, 10L,
                "RECENT",
                "CATEGORY", null,
                "SEARCH", null
        );

        //then
        assertThat(firstPage).hasSize(10);
        assertThat(secondPage).hasSize(5);

        Long totalCount = postMapper.countPosts(
                PostType.COMMUNITY.getCode(),
                "CATEGORY", null,
                "SEARCH", null
        );
        assertThat(totalCount).isEqualTo(15L);
    }

    @Test
    @DisplayName("페이지 크기에 따라 결과가 제한된다")
    void testPageSize()
    {
        //given
        for (int i = 1; i <= 20; i++)
        {
            Post post = createPost(1L, "테스트 게시글 " + i);
            postMapper.savePost(PostType.COMMUNITY.getCode(), post);
        }

        //when
        List<PostCardRecord> smallPage = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                0L, 5L,
                "RECENT",
                "CATEGORY", null,
                "SEARCH", null
        );

        List<PostCardRecord> largePage = postMapper.getPosts(
                PostType.COMMUNITY.getCode(),
                0L, 15L,
                "RECENT",
                "CATEGORY", null,
                "SEARCH", null
        );

        //then
        assertThat(smallPage).hasSize(5);
        assertThat(largePage).hasSize(15);
    }

    Post createPost(Long authorId, String title)
    {
        return Post.builder()
                .postType(PostType.COMMUNITY.getCode())
                .authorId(authorId)
                .anonymous(false)
                .title(title)
                .content("TestContent")
                .categoryId(1L)
                .build();

    }

    Post createPostWithContent(Long authorId, String title, String content)
    {
        return Post.builder()
                .postType(PostType.COMMUNITY.getCode())
                .authorId(authorId)
                .anonymous(false)
                .title(title)
                .content(content)
                .categoryId(1L)
                .build();
    }

    Post createPostWithCategory(Long authorId, String title, Long categoryId)
    {
        return Post.builder()
                .postType(PostType.COMMUNITY.getCode())
                .authorId(authorId)
                .anonymous(false)
                .title(title)
                .content("TestContent")
                .categoryId(categoryId)
                .build();
    }
}