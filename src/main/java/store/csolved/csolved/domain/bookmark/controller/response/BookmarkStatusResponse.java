package store.csolved.csolved.domain.bookmark.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookmarkStatusResponse
{
    boolean bookmarked;

    public static BookmarkStatusResponse from(boolean bookmarked)
    {
        return BookmarkStatusResponse.builder()
                .bookmarked(bookmarked)
                .build();
    }
}
