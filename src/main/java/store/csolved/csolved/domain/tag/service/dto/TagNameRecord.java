package store.csolved.csolved.domain.tag.service.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
public class TagNameRecord
{
    private Long id;
    private Long questionId;
    private String name;
}
