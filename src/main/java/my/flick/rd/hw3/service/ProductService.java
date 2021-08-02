package my.flick.rd.hw3.service;

import my.flick.rd.hw3.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    ProductDto getProductById(long id);

    ProductDto addProduct(ProductDto product);

    ProductDto updateProduct(long id, ProductDto productDto);

    void deleteProduct(long id);

}
