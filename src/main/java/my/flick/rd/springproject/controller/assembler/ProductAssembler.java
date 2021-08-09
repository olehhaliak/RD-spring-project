package my.flick.rd.springproject.controller.assembler;

import my.flick.rd.springproject.controller.ProductController;
import my.flick.rd.springproject.controller.model.ProductModel;
import my.flick.rd.springproject.dto.ProductDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductAssembler extends RepresentationModelAssemblerSupport<ProductDto, ProductModel> {

    public ProductAssembler() {
        super(ProductDto.class, ProductModel.class);
    }

    @Override
    public ProductModel toModel(ProductDto entity) {
        ProductModel model = new ProductModel(entity);
        Link getById = WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).getProduct(entity.getId())).withSelfRel();
        Link getAll = WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).getProducts(null)).withRel("get_all");
        Link create = WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).addProduct(null)).withRel("add");
        Link update = WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).updateProduct(entity.getId(),null)).withRel("update");
        Link delete = WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).deleteProduct(entity.getId())).withRel("delete");
        model.add(getById,getAll,create,update,delete);
        return model;
    }
}
