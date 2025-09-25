package store.csolved.csolved.utils.search;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Searching
{
    private String searchType;
    private String searchKeyword;

    public static Searching create(String searchType,
                                   String keyword)
    {
        return Searching.builder()
                .searchType(searchType)
                .searchKeyword(keyword)
                .build();
    }
}
