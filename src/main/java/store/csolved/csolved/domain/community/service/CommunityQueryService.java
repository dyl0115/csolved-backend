package store.csolved.csolved.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.domain.community.Community;
import store.csolved.csolved.domain.community.controller.dto.response.CommunityDetailResponse;
import store.csolved.csolved.domain.community.controller.dto.response.CommunityListResponse;
import store.csolved.csolved.domain.community.mapper.CommunityMapper;
import store.csolved.csolved.utils.filter.Filtering;
import store.csolved.csolved.utils.page.Pagination;
import store.csolved.csolved.utils.page.PaginationManager;
import store.csolved.csolved.utils.search.Searching;
import store.csolved.csolved.utils.sort.Sorting;

import java.util.List;

import static store.csolved.csolved.common.PostType.COMMUNITY;

@RequiredArgsConstructor
@Service
public class CommunityQueryService
{
    private final PaginationManager paginationManager;
    private final CommunityMapper communityMapper;

    // 커뮤니티글 리스트 조회
    public CommunityListResponse getCommunityPosts(Long pageNumber,
                                                   Sorting sort,
                                                   Filtering filter,
                                                   Searching search)
    {
        // DB에서 커뮤니티글 개수를 가져옴
        Long totalPosts = communityMapper.countCommunities(
                COMMUNITY.getCode(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());

        // 사용자가 요청한 페이지 번호, 글 개수를 사용하여 페이지 정보를 생성
        Pagination pagination = paginationManager.createPagination(pageNumber, totalPosts);

        // 페이지 정보를 사용하여 DB에 필요한 커뮤니티글만 조회
        List<Community> posts = communityMapper.getCommunities(COMMUNITY.getCode(),
                pagination.getOffset(),
                pagination.getSize(),
                sort.name(),
                filter.getFilterType(),
                filter.getFilterValue(),
                search.getSearchType(),
                search.getKeyword());

        return CommunityListResponse.from(pagination, posts);
    }

    // 커뮤니티글 상세 조회
    public CommunityDetailResponse getCommunityPost(Long postId)
    {
        Community community = communityMapper.getCommunity(postId);
        return CommunityDetailResponse.from(community);
    }
}
