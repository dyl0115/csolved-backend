package store.csolved.csolved.domain.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.csolved.csolved.domain.post.exception.AlreadyLikedException;
import store.csolved.csolved.domain.post.mapper.PostMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostLikeServiceTest
{
    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostLikeService postLikeService;

    @Test
    @DisplayName("게시글에 좋아요를 성공적으로 추가한다")
    void addLike()
    {
        //given
        Long postId = 1L;
        Long userId = 1L;

        when(postMapper.hasUserLiked(postId, userId)).thenReturn(false);

        //when
        postLikeService.addLike(postId, userId);

        //then
        verify(postMapper).hasUserLiked(postId, userId);
        verify(postMapper).addUserLike(postId, userId);
        verify(postMapper).increaseLikes(postId);
    }

    @Test
    @DisplayName("이미 좋아요한 게시글에 다시 좋아요를 시도하면 AlreadyLikedException이 발생한다")
    void addLike_AlreadyLiked()
    {
        //given
        Long postId = 1L;
        Long userId = 1L;

        when(postMapper.hasUserLiked(postId, userId)).thenReturn(true);

        //when & then
        assertThrows(AlreadyLikedException.class, () -> {
            postLikeService.addLike(postId, userId);
        });

        verify(postMapper).hasUserLiked(postId, userId);
        verify(postMapper, never()).addUserLike(any(), any());
        verify(postMapper, never()).increaseLikes(any());
    }
}