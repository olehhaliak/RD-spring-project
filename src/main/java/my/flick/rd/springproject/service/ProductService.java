package my.flick.rd.springproject.service;

import my.flick.rd.springproject.dto.ProductDto;
import my.flick.rd.springproject.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    ProductDto getProductById(long id);


    Product getProduct(Product product);

    ProductDto addProduct(ProductDto product);

    ProductDto updateProduct(long id, ProductDto productDto);

    void deleteProduct(long id);

    boolean exists(long productId);
}
