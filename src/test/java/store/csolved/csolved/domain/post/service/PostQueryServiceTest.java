package store.csolved.csolved.domain.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.csolved.csolved.domain.post.controller.response.PostDetailResponse;
import store.csolved.csolved.domain.post.controller.response.PostListResponse;
import store.csolved.csolved.domain.post.exception.PostNotFoundException;
import store.csolved.csolved.domain.post.mapper.PostMapper;
import store.csolved.csolved.domain.post.mapper.record.PostCardRecord;
import store.csolved.csolved.domain.post.mapper.record.PostDetailRecord;
import store.csolved.csolved.domain.tag.Tag;
import store.csolved.csolved.utils.filter.Filtering;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.page.PaginationManager;
import store.csolved.csolved.utils.search.Searching;
import store.csolved.csolved.utils.sort.Sorting;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static store.csolved.csolved.domain.post.PostType.COMMUNITY;

@ExtendWith(MockitoExtension.class)
class PostQueryServiceTest
{
    @Mock
    private PaginationManager paginationManager;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostQueryService postQueryService;

    @Test
    @DisplayName("게시글 목록을 성공적으로 조회한다")
    void getPosts()
    {
        //given
        Long pageNumber = 1L;
        Sorting sort = Sorting.RECENT;
        Filtering filter = Filtering.create("CATEGORY", null);
        Searching search = Searching.create("AUTHOR", null);

        Long totalPosts = 10L;
        Pagination pagination = Pagination.create(pageNumber, totalPosts);
        List<PostCardRecord> posts = createPostCards();

        when(postMapper.countPosts(
                COMMUNITY.getCode(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getSearchKeyword())).thenReturn(totalPosts);

        when(paginationManager.createPagination(pageNumber, totalPosts)).thenReturn(pagination);

        when(postMapper.getPosts(
                COMMUNITY.getCode(),
                pagination.getOffset(),
                pagination.getSize(),
                sort.name(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getSearchKeyword())).thenReturn(posts);

        //when
        PostListResponse response = postQueryService.getPosts(pageNumber, sort, filter, search);

        //then
        assertNotNull(response);
        verify(postMapper).countPosts(
                COMMUNITY.getCode(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getSearchKeyword());
        verify(paginationManager).createPagination(pageNumber, totalPosts);
        verify(postMapper).getPosts(
                COMMUNITY.getCode(),
                pagination.getOffset(),
                pagination.getSize(),
                sort.name(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getSearchKeyword());
    }

    @Test
    @DisplayName("게시글 상세 정보를 성공적으로 조회한다")
    void getPost()
    {
        //given
        Long postId = 1L;
        PostDetailRecord postDetail = createPostDetail();

        when(postMapper.getPost(postId)).thenReturn(postDetail);

        //when
        PostDetailResponse response = postQueryService.getPost(postId);

        //then
        assertNotNull(response);
        verify(postMapper).getPost(postId);
        verify(postMapper).increaseView(postId);
    }

    @Test
    @DisplayName("존재하지 않는 게시글을 조회하면 PostNotFoundException이 발생한다")
    void getPost_PostNotFound()
    {
        //given
        Long postId = 1L;

        when(postMapper.getPost(postId)).thenReturn(null);

        //when & then
        assertThrows(PostNotFoundException.class, () ->
        {
            postQueryService.getPost(postId);
        });

        verify(postMapper).getPost(postId);
        verify(postMapper, never()).increaseView(any());
    }



    private List<PostCardRecord> createPostCards()
    {
        Tag tag1 = createTag(1L, "Java");
        Tag tag2 = createTag(2L, "Spring");

        return List.of(
                PostCardRecord.builder()
                        .postId(1L)
                        .postType(1L)
                        .title("Test Title 1")
                        .anonymous(false)
                        .authorId(1L)
                        .authorNickname("author1")
                        .categoryId(1L)
                        .categoryName("General")
                        .tags(List.of(tag1))
                        .views(10L)
                        .likes(5L)
                        .answerCount(0L)
                        .createdAt(LocalDateTime.now())
                        .build(),
                PostCardRecord.builder()
                        .postId(2L)
                        .postType(1L)
                        .title("Test Title 2")
                        .anonymous(true)
                        .authorId(2L)
                        .authorNickname("author2")
                        .categoryId(2L)
                        .categoryName("Discussion")
                        .tags(List.of(tag2))
                        .views(15L)
                        .likes(8L)
                        .answerCount(1L)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    private PostDetailRecord createPostDetail()
    {
        Tag tag1 = createTag(1L, "Java");
        Tag tag2 = createTag(2L, "Spring");

        return PostDetailRecord.builder()
                .postType("COMMUNITY")
                .title("Test Title")
                .anonymous(false)
                .authorId(1L)
                .authorNickname("testauthor")
                .content("Test Content")
                .views(10L)
                .likes(5L)
                .answerCount(0L)
                .categoryId(1L)
                .categoryName("General")
                .tags(List.of(tag1, tag2))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private Tag createTag(Long id, String name)
    {
        return Tag.builder()
                .id(id)
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
    }
}