package store.csolved.csolved.domain.category.controller.response;

import lombok.Builder;
import lombok.Data;
import store.csolved.csolved.domain.category.Category;

import java.util.List;

@Data
@Builder
public class CategoryResponse
{
    List<Category> categories;


}
