package store.csolved.csolved.domain.common;

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
    private final Long rowCount;

    public static Page create(Long requestCurrentPage, Long totalRecordsCount)
    {
        Long currentPage = createCurrentPage(requestCurrentPage, totalRecordsCount);
        Long totalPage = createTotalPage(totalRecordsCount);
        Long offset = createOffset(currentPage);
        Long rowCount = createRowCount();

        return new Page(
                currentPage,
                totalPage,
                offset,
                rowCount);
    }

    private static Long createCurrentPage(Long currentPage, Long totalRecordsCount)
    {
        if (currentPage < 1L) return 1L;
        else if (currentPage > createTotalPage(totalRecordsCount)) return createTotalPage(totalRecordsCount);
        return currentPage;
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
