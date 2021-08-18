package my.flick.rd.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.flick.rd.springproject.annotation.RequireAdminPrivileges;
import my.flick.rd.springproject.dto.ProductDto;
import my.flick.rd.springproject.exception.CategoryNotFoundException;
import my.flick.rd.springproject.model.Product;
import my.flick.rd.springproject.exception.ProductNotFoundException;
import my.flick.rd.springproject.model.ProductSearchTemplate;
import my.flick.rd.springproject.repository.ProductRepository;
import my.flick.rd.springproject.service.CategoryService;
import my.flick.rd.springproject.service.ProductService;
import my.flick.rd.springproject.util.mapper.ProductDtoMapper;
import my.flick.rd.springproject.util.ProductTemplateParser;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final ProductDtoMapper dtoMapper;
    private final ProductTemplateParser templateParser;

    @Override
    public List<ProductDto> getProducts(ProductSearchTemplate searchTemplate) {
        if (searchTemplate == null) {
            return getAllProducts();
        }
        return getProductsByTemplate(searchTemplate);
    }

    private List<ProductDto> getProductsByTemplate(ProductSearchTemplate searchTemplate) {
        Page<Product> products = productRepository.findBySearchParams(
                searchTemplate.getInCategory(),
                searchTemplate.getWithMinPrice(),
                searchTemplate.getWithMaxPrice(),
               templateParser.parseToPageable(searchTemplate)
        );
        log.info(products.toString());
        products.get().forEach(System.out::println);
        return products.stream().map(dtoMapper::mapToDto).collect(Collectors.toList());
    }

    private List<ProductDto> getAllProducts() {
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
    @Transactional
    @RequireAdminPrivileges
    public ProductDto addProduct(ProductDto productRequestDto) {
        if (!categoryService.existsById(productRequestDto.getCategoryId())) {
            throw new CategoryNotFoundException("category with id specified does not exists");
        }
        Product product = dtoMapper.mapToModel(productRequestDto);
        product = productRepository.save(product);
        return dtoMapper.mapToDto(product);
    }


    @Override
    @Transactional
    @RequireAdminPrivileges
    public ProductDto updateProduct(long id, ProductDto productDto) {
        if (!categoryService.existsById(productDto.getCategoryId())) {
            throw new CategoryNotFoundException("category with id specified does not exists");
        }
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product you are trying to update does not exist"));
        Product product = dtoMapper.mapToModel(productDto);
        product.setId(id);
        product.setCreationTime(oldProduct.getCreationTime());
        product = productRepository.save(product);
        return dtoMapper.mapToDto(product);
    }

    @Override
    @Transactional
    @RequireAdminPrivileges
    public void deleteProduct(long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product you are trying to delete does not exist");
        }
        productRepository.deleteById(id);
    }

    @Override
    public boolean exists(long id) {
        return productRepository.existsById(id);
    }
}
