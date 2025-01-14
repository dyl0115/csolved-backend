package store.csolved.csolved.domain.tag.service.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
public class QuestionTagNameRecord
{
    private Long questionId;
    private List<TagNameRecord> tags;
}
