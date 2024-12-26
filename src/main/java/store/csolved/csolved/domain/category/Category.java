package store.csolved.csolved.domain.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class Category
{
    private final Long id;

    private final String name;

    private final LocalDateTime createdAt;

    private final LocalDateTime deletedAt;

    public static Category create(String name)
    {
        return new Category(null, name,
                LocalDateTime.now(), null);
    }
}
