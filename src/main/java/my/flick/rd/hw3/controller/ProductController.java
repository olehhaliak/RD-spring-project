package my.flick.rd.hw3.controller;

import lombok.AllArgsConstructor;
import my.flick.rd.hw3.dto.ProductDto;
import my.flick.rd.hw3.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    ProductDto getProduct(@PathVariable("id") long id) {
        return productService.getProductById(id);
    }

    /**
     * @param product
     * @return cratet product`s id
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductDto addProduct(@RequestBody ProductDto product) {
        return productService.addProduct(product);
    }


    @PutMapping("/{id}")
    ProductDto updateProduct(@PathVariable("id") long id, @RequestBody ProductDto productDto) {
       return productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduct(@PathVariable("id")long id) {
        productService.deleteProduct(id);
    }


}
