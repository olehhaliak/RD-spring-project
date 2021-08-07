package my.flick.rd.springproject.controller;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.api.CategoryApi;
import my.flick.rd.springproject.controller.model.CategoryModel;
import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {
    private final CategoryService categoryService;

    //Todo: replace new CategoryModel with assembler
    @Override
    public CategoryModel getCategoryById(long id) {
        return new CategoryModel(categoryService.getCategoryById(id));
    }

    @Override
    public CategoryModel addCategory(CategoryDto categoryDto) {
        return new CategoryModel(categoryService.addCategory(categoryDto));
    }

    @Override
    public CategoryModel updateCategory(long id, CategoryDto categoryDto) {
        return new CategoryModel(categoryService.updateCategory(id, categoryDto));
    }

    @Override
    public void deleteCategory(long id) {
        categoryService.deleteCategory(id);
    }

    @Override
    public List<CategoryModel> getSubcategories(long id) {
       return categoryService.getSubcategories(id).stream().map(CategoryModel::new).collect(Collectors.toList());
    }
}