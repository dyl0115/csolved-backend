package store.csolved.csolved.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.csolved.csolved.domain.community.Community;
import store.csolved.csolved.domain.community.mapper.CommunityMapper;
import store.csolved.csolved.utils.filter.Filtering;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.search.Searching;
import store.csolved.csolved.utils.sort.Sorting;

import java.util.List;

import static store.csolved.csolved.common.PostType.COMMUNITY;

@RequiredArgsConstructor
@Service
public class CommunityService
{
    private final CommunityMapper communityMapper;

    @Transactional
    public Long save(Community community)
    {
        communityMapper.saveCommunity(COMMUNITY.getCode(), community);
        return community.getId();
    }

    public Long countCommunities(Filtering filter, Searching search)
    {
        return communityMapper.countCommunities(
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


    public boolean checkAlreadyLike(Long communityId, Long userId)
    {
        return communityMapper.hasUserLiked(communityId, userId);
    }

    @Transactional
    public void addLike(Long communityId, Long userId)
    {
        communityMapper.addUserLike(communityId, userId);
        communityMapper.increaseLikes(communityId);
    }
}