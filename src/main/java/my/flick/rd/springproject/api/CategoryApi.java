package my.flick.rd.springproject.api;

import my.flick.rd.springproject.controller.model.CategoryModel;
import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

public interface CategoryApi {
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryModel getCategoryById(@PathVariable("id") long id);

    @GetMapping("/sub/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<CategoryModel> getSubcategories(@PathVariable("id") long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryModel addCategory(@RequestBody CategoryDto categoryDto);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryModel updateCategory(@PathVariable("id") long id, @RequestBody CategoryDto categoryDto);

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@PathVariable long id);
}
