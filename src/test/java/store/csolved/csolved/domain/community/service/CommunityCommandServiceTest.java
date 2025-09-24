package store.csolved.csolved.domain.community.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import store.csolved.csolved.domain.community.Community;
import store.csolved.csolved.domain.community.exception.DeleteDeniedException;
import store.csolved.csolved.domain.community.exception.PostNotFoundException;
import store.csolved.csolved.domain.community.exception.UpdateDeniedException;
import store.csolved.csolved.domain.community.mapper.CommunityMapper;
import store.csolved.csolved.domain.community.service.command.CommunityCreateCommand;
import store.csolved.csolved.domain.community.service.command.CommunityUpdateCommand;
import store.csolved.csolved.domain.tag.Tag;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static store.csolved.csolved.common.PostType.COMMUNITY;

@ExtendWith(MockitoExtension.class)
class CommunityCommandServiceTest
{
    @Mock
    private TagService tagService;

    @Mock
    private CommunityMapper communityMapper;

    @InjectMocks
    private CommunityCommandService communityCommandService;

    private CommunityCreateCommand createCommand;
    private CommunityUpdateCommand updateCommand;
    private List<Tag> tags;

    @BeforeEach
    void setUp()
    {
        tags = Arrays.asList(
                Tag.builder().name("Java").build(),
                Tag.builder().name("Spring").build()
        );

        createCommand = CommunityCreateCommand.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .authorId(1L)
                .anonymous(false)
                .categoryId(1L)
                .tags(tags)
                .build();

        updateCommand = CommunityUpdateCommand.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .anonymous(false)
                .categoryId(1L)
                .tags(tags)
                .build();
    }

    @Test
    @DisplayName("커뮤니티 글 저장에 성공해야 한다")
    void save_success()
    {
        // given
        ArgumentCaptor<Community> communityCaptor = ArgumentCaptor.forClass(Community.class);

        // when
        communityCommandService.save(createCommand);

        // then
        verify(communityMapper, times(1)).saveCommunity(eq(COMMUNITY.getCode()), communityCaptor.capture());
        verify(tagService, times(1)).saveTags(any(), eq(tags)); // ID는 Community 객체의 ID를 사용하므로 any()로 검증

        Community savedCommunity = communityCaptor.getValue();
        assertEquals("테스트 제목", savedCommunity.getTitle());
        assertEquals("테스트 내용", savedCommunity.getContent());
        assertEquals(1L, savedCommunity.getAuthorId());
    }

    @Test
    @DisplayName("커뮤니티 글 수정에 성공해야 한다")
    void update_success()
    {
        // given
        Long userId = 1L;
        Long postId = 1L;
        when(communityMapper.getAuthorId(postId)).thenReturn(userId);

        // when
        communityCommandService.update(userId, postId, updateCommand);

        // then
        verify(communityMapper, times(1)).getAuthorId(postId);
        verify(communityMapper, times(1)).updateCommunity(eq(postId), any(Community.class));
        verify(tagService, times(1)).updateTags(postId, tags);
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정 시 PostNotFoundException이 발생해야 한다")
    void update_postNotFound_throwsException()
    {
        // given
        Long userId = 1L;
        Long postId = 1L;
        when(communityMapper.getAuthorId(postId)).thenReturn(null);

        // when & then
        assertThrows(PostNotFoundException.class,
                () -> communityCommandService.update(userId, postId, updateCommand));

        verify(communityMapper, times(1)).getAuthorId(postId);
        verify(communityMapper, never()).updateCommunity(anyLong(), any(Community.class));
        verify(tagService, never()).updateTags(anyLong(), anyList());
    }

    @Test
    @DisplayName("작성자가 아닌 사용자가 수정 시 UpdateDeniedException이 발생해야 한다")
    void update_notAuthor_throwsException()
    {
        // given
        Long userId = 1L;
        Long authorId = 2L;
        Long postId = 1L;
        when(communityMapper.getAuthorId(postId)).thenReturn(authorId);

        // when & then
        assertThrows(UpdateDeniedException.class,
                () -> communityCommandService.update(userId, postId, updateCommand));

        verify(communityMapper, times(1)).getAuthorId(postId);
        verify(communityMapper, never()).updateCommunity(anyLong(), any(Community.class));
        verify(tagService, never()).updateTags(anyLong(), anyList());
    }

    @Test
    @DisplayName("커뮤니티 글 삭제에 성공해야 한다")
    void delete_success()
    {
        // given
        Long userId = 1L;
        Long postId = 1L;
        when(communityMapper.getAuthorId(postId)).thenReturn(userId);

        // when
        communityCommandService.delete(userId, postId);

        // then
        verify(communityMapper, times(1)).getAuthorId(postId);
        verify(communityMapper, times(1)).deleteCommunity(postId);
    }

    @Test
    @DisplayName("존재하지 않는 게시글 삭제 시 PostNotFoundException이 발생해야 한다")
    void delete_postNotFound_throwsException()
    {
        // given
        Long userId = 1L;
        Long postId = 1L;
        when(communityMapper.getAuthorId(postId)).thenReturn(null);

        // when & then
        assertThrows(PostNotFoundException.class,
                () -> communityCommandService.delete(userId, postId));

        verify(communityMapper, times(1)).getAuthorId(postId);
        verify(communityMapper, never()).deleteCommunity(anyLong());
    }

    @Test
    @DisplayName("작성자가 아닌 사용자가 삭제 시 DeleteDeniedException이 발생해야 한다")
    void delete_notAuthor_throwsException()
    {
        // given
        Long userId = 1L;
        Long authorId = 2L;
        Long postId = 1L;
        when(communityMapper.getAuthorId(postId)).thenReturn(authorId);

        // when & then
        assertThrows(DeleteDeniedException.class,
                () -> communityCommandService.delete(userId, postId));

        verify(communityMapper, times(1)).getAuthorId(postId);
        verify(communityMapper, never()).deleteCommunity(anyLong());
    }
}