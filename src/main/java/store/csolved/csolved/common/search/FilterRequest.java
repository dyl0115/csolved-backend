package store.csolved.csolved.common.search;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FilterRequest
{
    private String filterType;
    private Long filterValue;

    public static FilterRequest create(String filterType,
                                       Long filterValue)
    {
        return FilterRequest.builder()
                .filterType(filterType)
                .filterValue(filterValue)
                .build();
    }
}
