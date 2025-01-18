package store.csolved.csolved.common.search;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchRequest
{
    private String searchType;
    private String keyword;

    public static SearchRequest create(String searchType,
                                       String keyword)
    {
        return SearchRequest.builder()
                .searchType(searchType)
                .keyword(keyword)
                .build();
    }
}
