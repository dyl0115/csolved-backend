package store.csolved.csolved.domain.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.common.BaseEntity;
import store.csolved.csolved.common.PostType;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity
{
    private int postType;
    private String name;

    public static Category create(PostType postType, String name)
    {
        return Category.builder()
                .postType(postType.getCode())
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
