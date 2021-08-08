package my.flick.rd.springproject.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import my.flick.rd.springproject.controller.model.CategoryModel;
import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Api(tags = "Category management api")
@RequestMapping("/api/v1/categories")
public interface CategoryApi {
    @ApiOperation("get by id")
    @ApiImplicitParam(name = "id", type = "path", required = true, paramType = "long")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryModel getCategoryById(@PathVariable("id") long id);

    @ApiOperation("get subcategories")
    @ApiImplicitParam(name = "id", type = "path", required = true, paramType = "long")
    @GetMapping("/sub/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<CategoryModel> getSubcategories(@PathVariable("id") long id);

    @ApiOperation("get root categories")
    @GetMapping("/sub")
    @ResponseStatus(HttpStatus.OK)
    List<CategoryModel> getRootCategories();

    @ApiOperation("add new category")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryModel addCategory(@RequestBody CategoryDto categoryDto);


    @ApiOperation("update category")
    @ApiImplicitParam(name = "id", type = "path", required = true, paramType = "long")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryModel updateCategory(@PathVariable("id") long id, @RequestBody CategoryDto categoryDto);

    @ApiOperation("delete category")
    @ApiImplicitParam(name = "id", type = "path", required = true, paramType = "long")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deleteCategory(@PathVariable long id);

}
