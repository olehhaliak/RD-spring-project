package my.flick.rd.springproject.controller;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.api.CategoryApi;
import my.flick.rd.springproject.controller.assembler.CategoryAssembler;
import my.flick.rd.springproject.controller.model.CategoryModel;
import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {
    private final CategoryService categoryService;
    private final CategoryAssembler categoryAssembler;

    @Override
    public CategoryModel getCategoryById(long id) {
        return categoryAssembler.toModel(categoryService.getCategoryById(id));
    }

    @Override
    public CategoryModel addCategory(CategoryDto categoryDto) {
        return categoryAssembler.toModel(categoryService.addCategory(categoryDto));
    }

    @Override
    public CategoryModel updateCategory(long id, CategoryDto categoryDto) {
        return categoryAssembler.toModel(categoryService.updateCategory(id, categoryDto));
    }

    @Override
    public ResponseEntity<Void> deleteCategory(long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<CategoryModel> getSubcategories(long id) {
       return categoryService.getSubcategories(id).stream().map(categoryAssembler::toModel).collect(Collectors.toList());
    }

    @Override
    public List<CategoryModel> getRootCategories() {
        return categoryService.getRootCategories().stream().map(categoryAssembler::toModel).collect(Collectors.toList());
    }
}
