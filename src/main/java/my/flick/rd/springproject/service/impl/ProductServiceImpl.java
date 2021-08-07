package my.flick.rd.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.flick.rd.springproject.dto.ProductDto;
import my.flick.rd.springproject.model.Product;
import my.flick.rd.springproject.exception.ProductNotFoundException;
import my.flick.rd.springproject.repository.ProductRepository;
import my.flick.rd.springproject.service.ProductService;
import my.flick.rd.springproject.util.dtomapper.ProductDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
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
                .orElseThrow(() -> new ProductNotFoundException("No Product with specified id was found"));
    }

    @Override
    public ProductDto addProduct(ProductDto productRequestDto) {
        Product product = dtoMapper.mapToModel(productRequestDto);
        product = productRepository.save(product);
        return dtoMapper.mapToDto(product);
    }


    @Override
    public ProductDto updateProduct(long id, ProductDto productDto) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product you are trying to update does not exist"));

        Product product = dtoMapper.mapToModel(productDto);
        product.setId(id);
        product.setCreationTime(oldProduct.getCreationTime());
        product = productRepository.save(product);
        return dtoMapper.mapToDto(product);
    }

    @Override
    public void deleteProduct(long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product you are trying to delete does not exist");
        }
        productRepository.deleteById(id);
    }

}
