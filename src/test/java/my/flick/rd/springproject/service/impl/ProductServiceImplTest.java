package my.flick.rd.springproject.service.impl;

import static my.flick.rd.springproject.test.utils.ProductTestData.*;

import my.flick.rd.springproject.exception.CategoryNotFoundException;
import my.flick.rd.springproject.exception.ProductNotFoundException;
import my.flick.rd.springproject.model.ProductSearchTemplate;
import my.flick.rd.springproject.model.enums.SortOption;
import my.flick.rd.springproject.repository.ProductRepository;
import my.flick.rd.springproject.service.CategoryService;
import my.flick.rd.springproject.test.utils.CategoryTestData;
import my.flick.rd.springproject.util.mapper.impl.ProductDtoMapperImpl;
import my.flick.rd.springproject.util.sorting.ProductSortingUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductDtoMapperImpl productDtoMapper;
    @Mock
    CategoryService categoryService;
    @Mock
    ProductSortingUtil sortingUtil;
    @InjectMocks
    ProductServiceImpl productService;


    @Test
    void getProductByIdTest() {
        when(productDtoMapper.mapToDto(PRODUCT)).thenReturn(PRODUCT_DTO);
        when(productRepository.findById(any())).thenReturn(Optional.of(PRODUCT));

        assertThat(productService.getProductById(PRODUCT_ID), is(PRODUCT_DTO));
    }

    @Test
    void getProductByIdProductNotFoundTest() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(0));
    }

    @Test
    void getAllProducts() {
        when(productDtoMapper.mapToDto(PRODUCT)).thenReturn(PRODUCT_DTO);
        when(productRepository.findAll()).thenReturn(List.of(PRODUCT));

        assertThat(productService.getProducts(null), contains(PRODUCT_DTO));
    }

    @Test
    void getProductByTemplate() {
        BigDecimal minPrice = new BigDecimal(100);
        BigDecimal maxPrice = new BigDecimal(200);
        SortOption sortOption = SortOption.PRICE;
        boolean desc = true;
        ProductSearchTemplate searchTemplate = ProductSearchTemplate.builder()
                .inCategory(CategoryTestData.CATEGORY_ID)
                .withMinPrice(minPrice)
                .withMaxPrice(maxPrice)
                .sortBy(sortOption)
                .descendingOrder(desc)
                .build();
        when(productDtoMapper.mapToDto(PRODUCT)).thenReturn(PRODUCT_DTO);
        when(productRepository.findBySearchParams(CategoryTestData.CATEGORY_ID, minPrice, maxPrice))
                .thenReturn(List.of(PRODUCT));
        when(sortingUtil.sortProducts(List.of(PRODUCT),sortOption,desc)).thenReturn(List.of(PRODUCT));
        assertThat(productService.getProducts(searchTemplate),contains(PRODUCT_DTO));
    }

    @Test
    void addProductTest() {
        when(productDtoMapper.mapToDto(PRODUCT)).thenReturn(PRODUCT_DTO);
        when(productDtoMapper.mapToModel(PRODUCT_DTO)).thenReturn(PRODUCT);
        when(productRepository.save(PRODUCT)).thenReturn(PRODUCT);
        when(categoryService.existsById(anyLong())).thenReturn(true);

        assertThat(productService.addProduct(PRODUCT_DTO), is(equalTo(PRODUCT_DTO)));
    }

    @Test
    void addProduct_CategoryNotExistsTest() {
        when(categoryService.existsById(anyLong())).thenReturn(false);
        assertThrows(CategoryNotFoundException.class, () -> productService.addProduct(PRODUCT_DTO));
    }

    @Test
    void updateProduct() {
        when(productDtoMapper.mapToDto(PRODUCT)).thenReturn(PRODUCT_DTO);
        when(productDtoMapper.mapToModel(PRODUCT_DTO)).thenReturn(PRODUCT);
        when(productRepository.save(PRODUCT)).thenReturn(PRODUCT);
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(PRODUCT));
        when(categoryService.existsById(anyLong())).thenReturn(true);
        assertThat(productService.updateProduct(PRODUCT_ID, PRODUCT_DTO), is(equalTo(PRODUCT_DTO)));
    }

    @Test
    void updateProductNotExists() {
        when((productRepository.findById(PRODUCT_ID))).thenReturn(Optional.empty());
        when(categoryService.existsById(anyLong())).thenReturn(true);
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(PRODUCT_ID, PRODUCT_DTO));
    }

    @Test
    void updateProduct_CategoryNotExistsTest() {
        when(categoryService.existsById(anyLong())).thenReturn(false);
        assertThrows(CategoryNotFoundException.class, () -> productService.updateProduct(PRODUCT_ID, PRODUCT_DTO));
    }

    @Test
    void deleteProductTest() {
        when(productRepository.existsById(PRODUCT_ID)).thenReturn(true);
        productService.deleteProduct(PRODUCT_ID);
        verify(productRepository).deleteById(PRODUCT_ID);
    }

    @Test
    void deleteProductNotExistsTest() {
        when(productRepository.existsById(PRODUCT_ID)).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(PRODUCT_ID));
    }

    @Test
    public void existsTest() {
        when(productRepository.existsById(PRODUCT_ID)).thenReturn(true);
        assertTrue(productService.exists(PRODUCT_ID));
        when(productRepository.existsById(PRODUCT_ID)).thenReturn(false);
        assertFalse(productService.exists(PRODUCT_ID));
    }

}