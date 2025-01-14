package store.csolved.csolved.domain.tag.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import store.csolved.csolved.domain.tag.service.dto.QuestionTagNameRecord;
import store.csolved.csolved.domain.tag.service.dto.TagNameRecord;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ToString
@Builder
@Getter
public class TagNameDTO
{
    private Long id;
    private String name;

    public static TagNameDTO from(TagNameRecord record)
    {
        return TagNameDTO.builder()
                .id(record.getId())
                .name(record.getName())
                .build();
    }

    public static List<TagNameDTO> from(List<TagNameRecord> records)
    {
        return records.stream()
                .map(TagNameDTO::from)
                .toList();
    }

    public static List<TagNameDTO> from(QuestionTagNameRecord record)
    {
        return record.getTags().stream()
                .map(TagNameDTO::from)
                .toList();
    }

    public static Map<Long, List<TagNameDTO>> from(Map<Long, QuestionTagNameRecord> records)
    {
        return records.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> from(e.getValue())));
    }
}