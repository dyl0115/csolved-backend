package store.csolved.csolved.domain.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Page
{
    public static final Long DEFAULT_OFFSET = 0L;
    public static final Long DEFAULT_LIMIT = 10L;

    private Long offset;
    private Long limit;
}
