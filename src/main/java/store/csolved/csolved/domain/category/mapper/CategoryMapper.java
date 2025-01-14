package store.csolved.csolved.domain.category.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.category.service.dto.CategoryDetailRecord;

import java.util.List;

@Mapper
public interface CategoryMapper
{
    List<CategoryDetailRecord> getAllCategories();
}
