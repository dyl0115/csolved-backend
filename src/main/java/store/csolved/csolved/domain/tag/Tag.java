package store.csolved.csolved.domain.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import store.csolved.csolved.global.common.BaseEntity;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity
{
    private String name;

    public static Tag from(String name)
    {
        return Tag.builder()
                .name(name)
                .build();
    }
}
