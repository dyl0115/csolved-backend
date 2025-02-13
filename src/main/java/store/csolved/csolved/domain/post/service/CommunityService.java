package store.csolved.csolved.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.post.entity.Community;
import store.csolved.csolved.domain.post.entity.Post;
import store.csolved.csolved.domain.post.entity.PostType;
import store.csolved.csolved.domain.post.mapper.CommunityMapper;
import store.csolved.csolved.domain.search.filter.Filtering;
import store.csolved.csolved.domain.search.page.Pagination;
import store.csolved.csolved.domain.search.search.Searching;
import store.csolved.csolved.domain.search.sort.Sorting;

import java.util.List;

import static store.csolved.csolved.domain.post.entity.PostType.*;

@RequiredArgsConstructor
@Service
public class CommunityService
{
    private final CommunityMapper communityMapper;

    @Transactional
    public Long save(Community community)
    {
        communityMapper.saveCommunity(community);
        return community.getId();
    }

    public Long countCommunities(Filtering filter, Searching search)
    {
        return communityMapper.countCommunity(
                COMMUNITY.getCode(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
    }

    public Community getCommunity(Long communityId)
    {
        return communityMapper.getCommunity(communityId);
    }

    public List<Community> getCommunities(Pagination page,
                                          Sorting sort,
                                          Filtering filter,
                                          Searching search)
    {
        return communityMapper.getCommunities(
                COMMUNITY.getCode(),
                page.getOffset(),
                page.getSize(),
                sort.name(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());
    }

    // 질문글의 조회수를 1만큼 올리고, 질문 상세를 보여줌.
    @Transactional
    public Community viewCommunity(Long communityId)
    {
        communityMapper.increaseView(communityId);
        return communityMapper.getCommunity(communityId);
    }

    @Transactional
    public Long update(Long communityId, Community community)
    {
        communityMapper.updateCommunity(communityId, community);
        return communityId;
    }

    @Transactional
    public void delete(Long communityId)
    {
        communityMapper.deleteCommunity(communityId);
    }

    @Transactional
    public boolean addLike(Long communityId, Long userId)
    {
        if (communityMapper.hasUserLiked(communityId, userId))
        {
            return false;
        }

        communityMapper.addUserLike(communityId, userId);
        communityMapper.increaseLikes(communityId);
        return true;
    }
}