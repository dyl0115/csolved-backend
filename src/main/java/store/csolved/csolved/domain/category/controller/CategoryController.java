package store.csolved.csolved.domain.category.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.csolved.csolved.domain.category.mapper.entity.Category;
import store.csolved.csolved.domain.category.controller.response.CategoryResponse;
import store.csolved.csolved.domain.category.service.CategoryService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController
{
    private final CategoryService categoryService;

    @GetMapping
    public CategoryResponse getCategories()
    {
        List<Category> categories = categoryService.getAllCategories(1);
        return CategoryResponse.builder()
                .categories(categories)
                .build();
    }
}
