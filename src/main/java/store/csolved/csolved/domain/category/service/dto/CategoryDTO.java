package store.csolved.csolved.domain.category.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryDTO
{
    private Long id;
    private String name;

    public static CategoryDTO from(CategoryDetailRecord record)
    {
        return CategoryDTO.builder()
                .id(record.getId())
                .name(record.getName())
                .build();
    }

    public static List<CategoryDTO> from(List<CategoryDetailRecord> records)
    {
        return records.stream()
                .map(CategoryDTO::from)
                .toList();
    }
}
