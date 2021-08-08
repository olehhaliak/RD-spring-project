package my.flick.rd.springproject.service;

import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {
    CategoryDto getCategoryById(long id);

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(long id,CategoryDto categoryDto);

    void deleteCategory(long id);

    /**
     * @param id
     * @return all subcategories of category with id specified
     * @throws CategoryNotFoundException if category with such id does not exists
     */
    List<CategoryDto> getSubcategories(long id) throws CategoryNotFoundException;

    List<CategoryDto> getRootCategories();

    boolean existsById(long id);
}
