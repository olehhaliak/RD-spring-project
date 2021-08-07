package my.flick.rd.springproject.service;

import my.flick.rd.springproject.dto.CategoryDto;

public interface CategoryService {
    CategoryDto getCategoryById(long id);

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(long id,CategoryDto categoryDto);

    void deleteCategory(long id);
}
