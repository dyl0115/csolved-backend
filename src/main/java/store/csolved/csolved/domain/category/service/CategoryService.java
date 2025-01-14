package store.csolved.csolved.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.csolved.csolved.domain.category.service.dto.CategoryDetailDTO;
import store.csolved.csolved.domain.category.service.dto.CategoryDetailRecord;
import store.csolved.csolved.domain.category.mapper.CategoryMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService
{
    private final CategoryMapper categoryMapper;

    public List<CategoryDetailDTO> getAllCategories()
    {
        List<CategoryDetailRecord> categories = categoryMapper.getAllCategories();
        return CategoryDetailDTO.from(categories);
    }
}
