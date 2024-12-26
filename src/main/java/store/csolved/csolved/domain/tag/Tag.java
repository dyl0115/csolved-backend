package store.csolved.csolved.domain.tag;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class Tag
{
    private final Long id;
    private final String name;

    public static Tag create(String name)
    {
        return new Tag(null, name);
    }
}
