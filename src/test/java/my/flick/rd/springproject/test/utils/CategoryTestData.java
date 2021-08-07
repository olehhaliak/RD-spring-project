package my.flick.rd.springproject.test.utils;

import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.model.Category;

public class CategoryTestData {
    public static final long CATEGORY_ID = 3L;
    public static final String CATEGORY_NAME = "Laptops";
    public static final Category CATEGORY_PARENT = Category.builder()
            .id(1L)
            .name("electronics")
            .parent(null)
            .build();

    public static final Category CATEGORY = Category.builder()
            .id(CATEGORY_ID)
            .name(CATEGORY_NAME)
            .parent(CATEGORY_PARENT)
            .build();

    public static final CategoryDto CATEGORY_DTO = CategoryDto.builder()
            .id(CATEGORY_ID)
            .name(CATEGORY_NAME)
            .parentId(CATEGORY_PARENT.getId())
            .build();


}
