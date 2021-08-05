package my.flick.rd.hw3.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.flick.rd.hw3.dto.ProductDto;
import my.flick.rd.hw3.model.Product;
import my.flick.rd.hw3.exception.DBRecordNotFoundException;
import my.flick.rd.hw3.repository.ProductRepository;
import my.flick.rd.hw3.service.ProductService;
import my.flick.rd.hw3.util.dtomapper.ProductDtoMapper;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PropertyUtilsBean propertyUtils;
    private final ProductDtoMapper dtoMapper;
    @Override
    public List<ProductDto> getAllProducts() {
        return ((List<Product>) productRepository.findAll()).stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(long id) {
        return productRepository
                .findById(id)
                .map(dtoMapper::mapToDto)
                .orElseThrow(()->new DBRecordNotFoundException("No Product with specified id was found"));
    }

    @Override
    public ProductDto addProduct(ProductDto productRequestDto) {

        Product product = dtoMapper.mapToModel(productRequestDto);
        log.info(product.toString());
        product = productRepository.save(product);
        return dtoMapper.mapToDto(product);
    }


    @Override
    public ProductDto updateProduct(long id, ProductDto productDto) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new DBRecordNotFoundException("Product you are trying to update does not exist"));

        Product product = dtoMapper.mapToModel(productDto);
        product.setId(id);
        product.setCreationTime(oldProduct.getCreationTime());
        product = productRepository.save(product);
        log.info(product.toString());
        return dtoMapper.mapToDto(product);
    }

    @Override
    public void deleteProduct(long id) {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }

}
