package store.csolved.csolved.domain.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class Category
{
    private final Long id;
    private final String name;
    private final LocalDateTime createdAt;

    public static Category create(String name)
    {
        return Category.builder()
                .name(name)
                .build();
    }
}
