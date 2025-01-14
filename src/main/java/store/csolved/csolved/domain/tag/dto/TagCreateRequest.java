package store.csolved.csolved.domain.tag.dto;

import lombok.Builder;
import lombok.Getter;
import store.csolved.csolved.domain.tag.Tag;

@Getter
@Builder
public class TagCreateRequest
{
    private final Long id;
    private final String name;

    public Tag toEntity()
    {
        return Tag.create(id, name);
    }

    public static TagCreateRequest of(Tag tag)
    {
        return TagCreateRequest.builder()
                .name(tag.getName())
                .build();
    }
}
