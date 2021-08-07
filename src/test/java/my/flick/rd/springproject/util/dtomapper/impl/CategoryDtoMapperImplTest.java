package my.flick.rd.springproject.util.dtomapper.impl;

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
    void mapToModelTest() {
        Category actualCategory = categoryDtoMapper.mapToModel(CATEGORY_DTO);
        assertAll(
                () -> assertEquals(CATEGORY.getId(), actualCategory.getId()),
                () -> assertEquals(CATEGORY.getName(), actualCategory.getName()),
                () -> assertEquals(CATEGORY.getParent().getId(), actualCategory.getParent().getId())
        );
    }
}