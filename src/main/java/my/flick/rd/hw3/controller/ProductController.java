package my.flick.rd.hw3.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import my.flick.rd.hw3.controller.assembler.ProductAssembler;
import my.flick.rd.hw3.controller.model.ProductModel;
import my.flick.rd.hw3.dto.ProductDto;
import my.flick.rd.hw3.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductAssembler productAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductModel> getAllProducts() {
        return productService.getAllProducts().stream().map(productAssembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductModel getProduct(@PathVariable("id") long id) {
        return productAssembler.toModel(productService.getProductById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductModel addProduct(@RequestBody ProductDto product) {

        return productAssembler.toModel(productService.addProduct(product));
    }


    @PutMapping("/{id}")
    public ProductModel updateProduct(@PathVariable("id") long id, @RequestBody ProductDto productDto) {
       return productAssembler.toModel(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id")long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


}
