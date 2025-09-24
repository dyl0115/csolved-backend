package store.csolved.csolved.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.community.Community;
import store.csolved.csolved.domain.community.controller.dto.request.CommunityCreateRequest;
import store.csolved.csolved.domain.community.controller.dto.request.CommunityUpdateRequest;
import store.csolved.csolved.domain.community.exception.DeleteDeniedException;
import store.csolved.csolved.domain.community.exception.PostNotFoundException;
import store.csolved.csolved.domain.community.exception.UpdateDeniedException;
import store.csolved.csolved.domain.community.mapper.CommunityMapper;
import store.csolved.csolved.domain.community.service.command.CommunityCreateCommand;
import store.csolved.csolved.domain.community.service.command.CommunityUpdateCommand;
import store.csolved.csolved.domain.tag.service.TagService;

import java.util.Objects;

import static store.csolved.csolved.common.PostType.COMMUNITY;

@RequiredArgsConstructor
@Service
public class CommunityCommandService
{
    private final TagService tagService;
    private final CommunityMapper communityMapper;

    // 커뮤니티 글, 태그 저장
    @Transactional
    public void save(CommunityCreateCommand command)
    {
        Community post = Community.from(command);
        communityMapper.saveCommunity(COMMUNITY.getCode(), post);
        tagService.saveTags(post.getId(), post.getTags());
    }

    // 커뮤니티글 업데이트
    @Transactional
    public void update(Long userId, Long postId, CommunityUpdateCommand command)
    {
        Community post = Community.from(command);

        Community prePost = communityMapper.getCommunity(postId);

        if (prePost == null)
        {
            throw new PostNotFoundException();
        }

        if (!Objects.equals(prePost.getAuthorId(), userId))
        {
            throw new UpdateDeniedException();
        }

        communityMapper.updateCommunity(postId, post);
        tagService.updateTags(postId, post.getTags());
    }

    // 커뮤니티글 삭제
    @Transactional
    public void delete(Long userId, Long postId)
    {
        Community prePost = communityMapper.getCommunity(postId);

        if (prePost == null)
        {
            throw new PostNotFoundException();
        }

        if (!Objects.equals(prePost.getAuthorId(), userId))
        {
            throw new DeleteDeniedException();
        }

        communityMapper.deleteCommunity(postId);
    }
}
