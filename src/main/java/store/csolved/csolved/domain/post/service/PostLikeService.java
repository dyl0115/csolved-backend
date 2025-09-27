package store.csolved.csolved.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.global.exception.CsolvedException;
import store.csolved.csolved.global.exception.ExceptionCode;
import store.csolved.csolved.domain.post.mapper.PostMapper;

@RequiredArgsConstructor
@Service
public class PostLikeService
{
    private final PostMapper postMapper;

    @Transactional
    public void addLike(Long postId, Long userId)
    {
        boolean alreadyLike = postMapper.hasUserLiked(postId, userId);

        if (alreadyLike)
        {
            throw new CsolvedException(ExceptionCode.ALREADY_LIKED);
        }

        postMapper.addUserLike(postId, userId);
        postMapper.increaseLikes(postId);
    }
}
