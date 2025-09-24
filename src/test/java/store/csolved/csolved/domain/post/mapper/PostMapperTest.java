package store.csolved.csolved.domain.post.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.annotation.DirtiesContext;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.post.mapper.entity.Post;
import store.csolved.csolved.domain.post.PostType;
import store.csolved.csolved.domain.post.mapper.record.PostCard;
import store.csolved.csolved.domain.post.mapper.record.PostDetail;
import store.csolved.csolved.utils.page.Pagination;

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

    @BeforeEach
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

    @Test
    @DisplayName("게시글을 저장하고 조회할 수 있다")
    void saveAndGetPost()
    {
        //given
        Post post = createPost(1L, "title1");
        postMapper.savePost(PostType.COMMUNITY.getCode(), post);

        //when & then
        PostDetail foundPost = postMapper.getPost(post.getId());

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
        PostDetail foundPost1 = postMapper.getPost(post1.getId());
        PostDetail foundPost2 = postMapper.getPost(post2.getId());
        PostDetail foundPost3 = postMapper.getPost(post3.getId());

        //then
        assertThat(Objects.equals(foundPost1.getTitle(), post1.getTitle())).isTrue();
        assertThat(Objects.equals(foundPost2.getTitle(), post2.getTitle())).isTrue();
        assertThat(Objects.equals(foundPost3.getTitle(), post3.getTitle())).isTrue();

        List<PostCard> posts = postMapper.getPosts(PostType.COMMUNITY.getCode(),
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
        PostDetail foundPost = postMapper.getPost(post.getId());
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
        PostDetail deletedPost = postMapper.getPost(post.getId());
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
        PostDetail updatedPost = postMapper.getPost(post.getId());
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
        PostDetail updatedPost = postMapper.getPost(post.getId());
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
        List<PostCard> repliedPosts = postMapper.getRepliedPosts(2L, page);

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
        List<PostCard> userPosts = postMapper.getUserPosts(1L, page);

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
}