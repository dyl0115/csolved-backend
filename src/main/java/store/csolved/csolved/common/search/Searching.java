package store.csolved.csolved.common.search;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
public class Searching
{
    private String searchType;
    private String keyword;

    public static Searching create(String searchType,
                                   String keyword)
    {
        return Searching.builder()
                .searchType(searchType)
                .keyword(keyword)
                .build();
    }
}
