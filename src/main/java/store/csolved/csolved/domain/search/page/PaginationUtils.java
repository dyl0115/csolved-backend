package store.csolved.csolved.domain.search.page;

import org.springframework.stereotype.Service;

@Service
public class PaginationUtils
{
    public Pagination createPagination(Long pageNumber, Long totalRecords)
    {
        return Pagination.create(pageNumber, totalRecords);
    }
}
