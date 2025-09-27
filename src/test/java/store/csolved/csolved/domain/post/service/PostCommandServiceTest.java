package store.csolved.csolved.domain.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.csolved.csolved.global.exception.CsolvedException;
import store.csolved.csolved.global.exception.ExceptionCode;
import store.csolved.csolved.domain.post.mapper.PostMapper;
import store.csolved.csolved.domain.post.mapper.entity.Post;
import store.csolved.csolved.domain.post.service.command.PostCreateCommand;
import store.csolved.csolved.domain.post.service.command.PostUpdateCommand;
import store.csolved.csolved.domain.tag.Tag;
import store.csolved.csolved.domain.tag.service.TagService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static store.csolved.csolved.domain.post.mapper.entity.PostType.COMMUNITY;

@ExtendWith(MockitoExtension.class)
class PostCommandServiceTest
{
    @Mock
    private TagService tagService;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostCommandService postCommandService;

    @Test
    @DisplayName("게시글을 성공적으로 저장한다")
    void save()
    {
        //given
        PostCreateCommand command = createPostCreateCommand();

        //when
        postCommandService.save(command);

        //then
        verify(postMapper).savePost(eq(COMMUNITY.getCode()), any(Post.class));
        verify(tagService).saveTags(any(), eq(command.getTags()));
    }

    @Test
    @DisplayName("게시글을 성공적으로 업데이트한다")
    void update()
    {
        //given
        Long userId = 1L;
        Long postId = 1L;
        PostUpdateCommand command = createPostUpdateCommand();

        when(postMapper.getAuthorId(postId)).thenReturn(userId);

        //when
        postCommandService.update(userId, postId, command);

        //then
        verify(postMapper).getAuthorId(postId);
        verify(postMapper).updatePost(eq(postId), any(Post.class));
        verify(tagService).updateTags(postId, command.getTags());
    }

    @Test
    @DisplayName("존재하지 않는 게시글을 업데이트하려 하면 PostNotFoundException이 발생한다")
    void update_PostNotFound()
    {
        //given
        Long userId = 1L;
        Long postId = 1L;
        PostUpdateCommand command = createPostUpdateCommand();

        when(postMapper.getAuthorId(postId)).thenReturn(null);

        //when & then
        CsolvedException exception = assertThrows(CsolvedException.class, () ->
        {
            postCommandService.update(userId, postId, command);
        });
        assertEquals(ExceptionCode.POST_NOT_FOUND, exception.getCode());

        verify(postMapper).getAuthorId(postId);
        verify(postMapper, never()).updatePost(any(), any());
        verify(tagService, never()).updateTags(any(), any());
    }

    @Test
    @DisplayName("다른 사용자의 게시글을 업데이트하려 하면 UpdateDeniedException이 발생한다")
    void update_UpdateDenied()
    {
        //given
        Long userId = 1L;
        Long postId = 1L;
        Long differentAuthorId = 2L;
        PostUpdateCommand command = createPostUpdateCommand();

        when(postMapper.getAuthorId(postId)).thenReturn(differentAuthorId);

        //when & then
        CsolvedException exception = assertThrows(CsolvedException.class, () ->
        {
            postCommandService.update(userId, postId, command);
        });
        assertEquals(ExceptionCode.POST_UPDATE_DENIED, exception.getCode());

        verify(postMapper).getAuthorId(postId);
        verify(postMapper, never()).updatePost(any(), any());
        verify(tagService, never()).updateTags(any(), any());
    }

    @Test
    @DisplayName("게시글을 성공적으로 삭제한다")
    void delete()
    {
        //given
        Long userId = 1L;
        Long postId = 1L;

        when(postMapper.getAuthorId(postId)).thenReturn(userId);

        //when
        postCommandService.delete(userId, postId);

        //then
        verify(postMapper).getAuthorId(postId);
        verify(postMapper).deletePost(postId);
    }

    @Test
    @DisplayName("존재하지 않는 게시글을 삭제하려 하면 PostNotFoundException이 발생한다")
    void delete_PostNotFound()
    {
        //given
        Long userId = 1L;
        Long postId = 1L;

        when(postMapper.getAuthorId(postId)).thenReturn(null);

        //when & then
        CsolvedException exception = assertThrows(CsolvedException.class, () ->
        {
            postCommandService.delete(userId, postId);
        });
        assertEquals(ExceptionCode.POST_NOT_FOUND, exception.getCode());

        verify(postMapper).getAuthorId(postId);
        verify(postMapper, never()).deletePost(any());
    }

    @Test
    @DisplayName("다른 사용자의 게시글을 삭제하려 하면 DeleteDeniedException이 발생한다")
    void delete_DeleteDenied()
    {
        //given
        Long userId = 1L;
        Long postId = 1L;
        Long differentAuthorId = 2L;

        when(postMapper.getAuthorId(postId)).thenReturn(differentAuthorId);

        //when & then
        CsolvedException exception = assertThrows(CsolvedException.class, () ->
        {
            postCommandService.delete(userId, postId);
        });
        assertEquals(ExceptionCode.POST_DELETE_DENIED, exception.getCode());

        verify(postMapper).getAuthorId(postId);
        verify(postMapper, never()).deletePost(any());
    }

    private PostCreateCommand createPostCreateCommand()
    {
        Tag tag1 = createTag(1L, "Java");
        Tag tag2 = createTag(2L, "Spring");

        return PostCreateCommand.builder()
                .authorId(1L)
                .anonymous(false)
                .title("Test Title")
                .content("Test Content")
                .categoryId(1L)
                .tags(List.of(tag1, tag2))
                .build();
    }

    private PostUpdateCommand createPostUpdateCommand()
    {
        Tag tag1 = createTag(1L, "Java");
        Tag tag2 = createTag(2L, "Test");

        return PostUpdateCommand.builder()
                .anonymous(true)
                .title("Updated Title")
                .content("Updated Content")
                .categoryId(2L)
                .tags(List.of(tag1, tag2))
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