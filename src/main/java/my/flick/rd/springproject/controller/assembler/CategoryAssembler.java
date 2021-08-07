package my.flick.rd.springproject.controller.assembler;

import my.flick.rd.springproject.controller.CategoryController;
import my.flick.rd.springproject.controller.model.CategoryModel;
import my.flick.rd.springproject.dto.CategoryDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class CategoryAssembler extends RepresentationModelAssemblerSupport<CategoryDto, CategoryModel> {
    public CategoryAssembler() {
        super(CategoryController.class, CategoryModel.class);
    }

    @Override
    public CategoryModel toModel(CategoryDto entity) {
        CategoryModel model = new CategoryModel(entity);
        model.add(WebMvcLinkBuilder.linkTo(methodOn(CategoryController.class).addCategory(null)).withRel("add"));
        model.add(WebMvcLinkBuilder.linkTo(methodOn(CategoryController.class).getCategoryById(entity.getId())).withSelfRel());
        model.add(WebMvcLinkBuilder.linkTo(methodOn(CategoryController.class).getSubcategories(entity.getId())).withRel("subcategories"));
        model.add(WebMvcLinkBuilder.linkTo(methodOn(CategoryController.class).updateCategory(entity.getId(), null)).withRel("update"));
        model.add(WebMvcLinkBuilder.linkTo(methodOn(CategoryController.class).deleteCategory(entity.getId())).withRel("update"));
        return model;
    }
}
