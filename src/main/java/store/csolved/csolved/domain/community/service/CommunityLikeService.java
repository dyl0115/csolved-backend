package store.csolved.csolved.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.community.exception.AlreadyLikedException;
import store.csolved.csolved.domain.community.mapper.CommunityMapper;

@RequiredArgsConstructor
@Service
public class CommunityLikeService
{
    private final CommunityMapper communityMapper;

    @Transactional
    public void addLike(Long postId, Long userId)
    {
        boolean alreadyLike = communityMapper.hasUserLiked(postId, userId);

        if (alreadyLike)
        {
            throw new AlreadyLikedException();
        }

        communityMapper.addUserLike(postId, userId);
        communityMapper.increaseLikes(postId);
    }
}
