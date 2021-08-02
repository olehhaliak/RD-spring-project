package my.flick.rd.hw3.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.flick.rd.hw3.dto.ProductDto;
import my.flick.rd.hw3.entity.Product;
import my.flick.rd.hw3.exception.DBRecordNotFoundException;
import my.flick.rd.hw3.repository.ProductRepository;
import my.flick.rd.hw3.service.ProductService;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PropertyUtilsBean propertyUtils;

    @Override
    public List<ProductDto> getAllProducts() {
        return ((List<Product>) productRepository.findAll()).stream()
                .map(this::mapProductToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(long id) {
        return productRepository
                .findById(id)
                .map(this::mapProductToProductDto)
                .orElseThrow(()->new DBRecordNotFoundException("No Product with specified id was found"));
    }

    @Override
    public ProductDto addProduct(ProductDto productRequestDto) {

        Product product = mapProductDtoToProduct(productRequestDto);
        log.info(product.toString());
        product = productRepository.save(product);
        return mapProductToProductDto(product);
    }


    @Override
    public ProductDto updateProduct(long id, ProductDto productDto) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new DBRecordNotFoundException("Product you are trying to update does not exist"));

        Product product = mapProductDtoToProduct(productDto);
        product.setId(id);
        product.setCreationTime(oldProduct.getCreationTime());
        product = productRepository.save(product);
        log.info(product.toString());
        return mapProductToProductDto(product);
    }

    @Override
    public void deleteProduct(long id) {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }

    //Todo: consider moving to another class
    Product mapProductDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        try {
            propertyUtils.copyProperties(product, productDto);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Failed to map ProductDto to Product", e);
        }
        return product;
    }

    ProductDto mapProductToProductDto(Product product) {

        ProductDto productDto = new ProductDto();
        try {
            propertyUtils.copyProperties(productDto, product);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Failed to map Product to ProductDto", e);
        }
        return productDto;
    }
}
