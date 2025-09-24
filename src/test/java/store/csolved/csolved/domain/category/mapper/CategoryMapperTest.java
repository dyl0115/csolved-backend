package store.csolved.csolved.domain.category.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import store.csolved.csolved.common.PostType;
import store.csolved.csolved.domain.category.Category;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@MybatisTest
class CategoryMapperTest
{
    @Autowired
    CategoryMapper categoryMapper;

    @BeforeEach
    void beforeEach()
    {
        categoryMapper.deleteAll();
    }

    @Test
    void save()
    {
        //given
        Category category = createTestCategory("testCategoryName");

        //when
        categoryMapper.save(category);

        //then
        assertThat(categoryMapper.getAll(category.getPostType()).size()).isEqualTo(1);
    }

    @Test
    void isExistByName()
    {
        //given
        String categoryName = "TestCategoryName";
        Category category = createTestCategory(categoryName);
        categoryMapper.save(category);

        //when
        boolean isExist = categoryMapper.isExistByName(categoryName);

        //then
        assertThat(isExist).isTrue();
    }

    Category createTestCategory(String categoryName)
    {
        return Category.builder()
                .postType(PostType.COMMUNITY.getCode())
                .name(categoryName)
                .createdAt(LocalDateTime.now())
                .build();
    }
}