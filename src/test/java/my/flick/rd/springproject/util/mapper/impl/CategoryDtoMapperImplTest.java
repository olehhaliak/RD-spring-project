package my.flick.rd.springproject.util.mapper.impl;

import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.model.Category;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static my.flick.rd.springproject.test.utils.CategoryTestData.*;

class CategoryDtoMapperImplTest {
    CategoryDtoMapperImpl categoryDtoMapper = new CategoryDtoMapperImpl();

    @Test
    void mapToDtoTest() {
        assertThat(categoryDtoMapper.mapToDto(CATEGORY), is(equalTo(CATEGORY_DTO)));
    }

    @Test
    void mapToDto_RootCategoryTest() {
        Category testCategory = Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build();
        CategoryDto testCategoryDto = CategoryDto.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build();
        assertThat(categoryDtoMapper.mapToDto(testCategory), is(equalTo(testCategoryDto)));
    }

    @Test
    void mapToModelTest() {
        Category actualCategory = categoryDtoMapper.mapToModel(CATEGORY_DTO);
        assertAll(
                () -> assertEquals(CATEGORY.getId(), actualCategory.getId()),
                () -> assertEquals(CATEGORY.getName(), actualCategory.getName()),
                () -> assertEquals(CATEGORY.getParent().getId(), actualCategory.getParent().getId())
        );
    }

    @Test
    void mapToModel_RootCategoryTest() {
        CategoryDto testCategoryDto = CategoryDto.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build();
        Category actualCategory = categoryDtoMapper.mapToModel(testCategoryDto);
        assertAll(
                () -> assertEquals(CATEGORY.getId(), actualCategory.getId()),
                () -> assertEquals(CATEGORY.getName(), actualCategory.getName()),
                () -> assertNull(actualCategory.getParent())
        );
    }


}