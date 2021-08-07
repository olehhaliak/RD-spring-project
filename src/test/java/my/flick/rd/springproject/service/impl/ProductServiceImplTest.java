package my.flick.rd.springproject.service.impl;

import static my.flick.rd.springproject.test.utils.ProductTestData.*;

import my.flick.rd.springproject.exception.ProductNotFoundException;
import my.flick.rd.springproject.repository.ProductRepository;
import my.flick.rd.springproject.util.dtomapper.impl.ProductDtoMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    static
    ProductDtoMapperImpl productDtoMapper;
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

        assertThat(productService.getAllProducts(), contains(PRODUCT_DTO));
    }

    @Test
    void addProductTest() {
        when(productDtoMapper.mapToDto(PRODUCT)).thenReturn(PRODUCT_DTO);
        when(productDtoMapper.mapToModel(PRODUCT_DTO)).thenReturn(PRODUCT);
        when(productRepository.save(PRODUCT)).thenReturn(PRODUCT);

        assertThat(productService.addProduct(PRODUCT_DTO), is(equalTo(PRODUCT_DTO)));
    }

    @Test
    void updateProduct() {
        when(productDtoMapper.mapToDto(PRODUCT)).thenReturn(PRODUCT_DTO);
        when(productDtoMapper.mapToModel(PRODUCT_DTO)).thenReturn(PRODUCT);
        when(productRepository.save(PRODUCT)).thenReturn(PRODUCT);
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(PRODUCT));

        assertThat(productService.updateProduct(PRODUCT_ID, PRODUCT_DTO), is(equalTo(PRODUCT_DTO)));
    }

    @Test
    void updateProductNotExists() {
        when((productRepository.findById(PRODUCT_ID))).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(PRODUCT_ID, PRODUCT_DTO));
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
}