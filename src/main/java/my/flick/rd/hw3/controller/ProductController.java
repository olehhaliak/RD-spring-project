package my.flick.rd.hw3.controller;

import lombok.AllArgsConstructor;
import my.flick.rd.hw3.dto.ProductRequestDto;
import my.flick.rd.hw3.entity.Product;
import my.flick.rd.hw3.service.ProductService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> getProduct(@PathVariable("id") long id) {
        Optional<Product> productOptional = productService.getProduct(id);
        if (productOptional.isPresent()) {
            return new ResponseEntity<Product>(productOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param product
     * @return cratet product`s id
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    long addProduct(@RequestBody ProductRequestDto product) {
               return productService.addProduct(product);
    }


    @PutMapping
    ResponseEntity<?> updateProduct(@RequestBody Product product) {
       if(productService.updateProduct(product)){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/")
    ResponseEntity<?> deleteProduct(@RequestParam long id) {
        if(productService.deleteProduct(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
