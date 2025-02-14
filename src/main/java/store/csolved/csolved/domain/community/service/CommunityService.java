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
        System.out.println("CommunityService ");
        System.out.println("    [postType] Community.getCode() = " + COMMUNITY.getCode());
        System.out.println("    [offset] page.getOffset() = " + page.getOffset());
        System.out.println("    [size] page.getSize() = " + page.getSize());
        System.out.println("    [sortType] sort.name() = " + sort.name());
        System.out.println("    [filterType] filter.getFilterType() = " + filter.getFilterType());
        System.out.println("    [filterValue] filter.getFilterValue() = " + filter.getFilterValue());
        System.out.println("    [searchType] search.getSearchType() = " + search.getSearchType());
        System.out.println("    [searchKeyword] search.getKeyword() = " + search.getKeyword());

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