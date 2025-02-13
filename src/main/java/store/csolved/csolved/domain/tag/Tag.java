package store.csolved.csolved.domain.tag;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Tag
{
    private final Long id;
    private final String name;

    public static Tag from(String name)
    {
        return Tag.builder()
                .name(name)
                .build();
    }
}
