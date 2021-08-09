package my.flick.rd.springproject.controller;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.api.ProductApi;
import my.flick.rd.springproject.controller.assembler.ProductAssembler;
import my.flick.rd.springproject.controller.model.ProductModel;
import my.flick.rd.springproject.dto.ProductDto;
import my.flick.rd.springproject.model.ProductSearchTemplate;
import my.flick.rd.springproject.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;
    private final ProductAssembler productAssembler;

    @Override
    public List<ProductModel> getProduct(ProductSearchTemplate searchTemplate) {
        return productService.getProducts(searchTemplate).stream().map(productAssembler::toModel).collect(Collectors.toList());
    }

    @Override
    public ProductModel getProduct(@PathVariable("id") long id) {
        return productAssembler.toModel(productService.getProductById(id));
    }

    @Override
    public ProductModel addProduct(@RequestBody ProductDto product) {
        return productAssembler.toModel(productService.addProduct(product));
    }


    @Override
    public ProductModel updateProduct(@PathVariable("id") long id, @RequestBody ProductDto productDto) {
       return productAssembler.toModel(productService.updateProduct(id, productDto));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(@PathVariable("id")long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
