package store.csolved.csolved.domain.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@ToString
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
