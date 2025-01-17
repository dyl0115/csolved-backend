package store.csolved.csolved.common.search;

import lombok.AllArgsConstructor;
import lombok.Data;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Data
public class PageDetailDTO
{
    private final Long currentPage;
    private final Long totalPage;
    private final Long offset;
    private final Long size;

    public static PageDetailDTO create(Long requestPage,
                                       Long totalRecordsCount,
                                       Long recordsCountOnSinglePage)
    {
        Long totalPage = createTotalPage(totalRecordsCount, recordsCountOnSinglePage);
        Long currentPage = createCurrentPage(requestPage, totalPage);
        Long offset = createOffset(currentPage, recordsCountOnSinglePage);

        return new PageDetailDTO(
                currentPage,
                totalPage,
                offset,
                recordsCountOnSinglePage);
    }

    private static Long createTotalPage(Long totalRecordsCount,
                                        Long recordsCountOnSinglePage)
    {
        return (totalRecordsCount + recordsCountOnSinglePage - 1) / recordsCountOnSinglePage;
    }

    private static Long createCurrentPage(Long requestPage,
                                          Long totalPage)
    {
        return Math.min(requestPage, totalPage);
    }

    private static Long createOffset(Long currentPage,
                                     Long recordsCountOnSinglePage)
    {
        return (currentPage - 1) * recordsCountOnSinglePage;
    }
}
