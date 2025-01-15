package store.csolved.csolved.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Data
public class Page
{
    public static final Long DEFAULT_SINGLE_PAGE_COUNT = 2L;

    private final Long currentPage;
    private final Long totalPage;
    private final Long offset;
    private final Long size;

    public static Page validateAndCreate(String requestPage, Long totalRecordsCount)
    {
        Long currentPage = createCurrentPage(requestPage, totalRecordsCount);
        Long totalPage = createTotalPage(totalRecordsCount);
        Long offset = createOffset(currentPage);
        Long rowCount = createRowCount();

        return new Page(
                currentPage,
                totalPage,
                offset,
                rowCount);
    }

    private static Long createCurrentPage(String requestPage, Long totalRecordsCount)
    {
        if (!requestPage.matches("^[1-9]\\d*$")) return 1L;
        return Math.min(Long.parseLong(requestPage), createTotalPage(totalRecordsCount));
    }

    private static Long createTotalPage(Long totalRecordsCount)
    {
        return (totalRecordsCount + DEFAULT_SINGLE_PAGE_COUNT - 1) / DEFAULT_SINGLE_PAGE_COUNT;
    }

    private static Long createOffset(Long currentPage)
    {
        return (currentPage - 1) * DEFAULT_SINGLE_PAGE_COUNT;
    }

    private static Long createRowCount()
    {
        return DEFAULT_SINGLE_PAGE_COUNT;
    }
}
