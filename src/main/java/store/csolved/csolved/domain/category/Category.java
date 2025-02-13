package store.csolved.csolved.domain.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.csolved.csolved.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity
{
    private String name;

    public static Category create(String name)
    {
        return Category.builder()
                .name(name)
                .build();
    }
}
