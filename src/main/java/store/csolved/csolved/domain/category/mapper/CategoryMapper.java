package store.csolved.csolved.domain.category.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.csolved.csolved.domain.category.Category;

import java.util.List;

@Mapper
public interface CategoryMapper
{
    void save(Category category);

    boolean isExistByName(String name);

    List<Category> getAll(int postTypeCode);

    void deleteAll();
}
