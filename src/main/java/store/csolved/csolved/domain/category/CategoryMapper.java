package store.csolved.csolved.domain.category;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper
{
    List<Category> findAllCategoryNames();
}
