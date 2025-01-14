package store.csolved.csolved.domain.category.service.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class CategoryDetailDTO
{
    private Long id;
    private String name;

    public static CategoryDetailDTO from(CategoryDetailRecord record)
    {
        return CategoryDetailDTO.builder()
                .id(record.getId())
                .name(record.getName())
                .build();
    }

    public static List<CategoryDetailDTO> from(List<CategoryDetailRecord> records)
    {
        return records.stream()
                .map(CategoryDetailDTO::from)
                .toList();
    }
}
