package my.flick.rd.springproject.service;

import my.flick.rd.springproject.dto.ProductDto;
import my.flick.rd.springproject.model.Product;
import my.flick.rd.springproject.model.ProductSearchTemplate;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts(ProductSearchTemplate searchTemplate);

    ProductDto getProductById(long id);



    ProductDto addProduct(ProductDto product);

    ProductDto updateProduct(long id, ProductDto productDto);

    void deleteProduct(long id);

    boolean exists(long productId);
}
