package store.csolved.csolved.domain.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class Tag
{
    private final Long questionId;
    private final String name;

    public static Tag create(Long questionId, String name)
    {
        return new Tag(questionId, name);
    }
}
