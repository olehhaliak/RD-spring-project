package my.flick.rd.springproject.intregration;

import my.flick.rd.springproject.dto.ProductDto;
import my.flick.rd.springproject.model.Category;
import my.flick.rd.springproject.model.Product;
import my.flick.rd.springproject.repository.CategoryRepository;
import my.flick.rd.springproject.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static my.flick.rd.springproject.test.utils.CategoryTestData.CATEGORY;
import static my.flick.rd.springproject.test.utils.ProductTestData.testProduct;
import static my.flick.rd.springproject.test.utils.ProductTestData.testProductDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class ProductTest {


    private String baseLink;
    private String getProductById = baseLink + "/{%ld}";
    private String addProduct = baseLink;
    private String updateProduct = baseLink + "/{%ld}";
    private String deleteProduct = baseLink + "/{%ld}";
    private Category testCategory;

    @Autowired
    public ProductTest(@Value("http://localhost:${local.server.port}/api/v1/products") String baseLink,
                       CategoryRepository categoryRepository) {
        this.baseLink = baseLink;
        getProductById = baseLink + "/%d";
        addProduct = baseLink;
        updateProduct = baseLink + "/%d";
        deleteProduct = baseLink + "/%d";
        testCategory = categoryRepository.save(CATEGORY);
    }

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ProductRepository productRepository;


    @AfterEach
    void clearDB() {
        productRepository.deleteAll();
    }

    @Test
    void addProductTest() {
        ProductDto productDto = testRestTemplate.postForObject(addProduct, testProductDto(), ProductDto.class);
        assertAll(
                () -> assertEquals(testProductDto().getName(), productDto.getName()),
                () -> assertEquals(testProductDto().getDescription(), productDto.getDescription()),
                () -> assertEquals(testProductDto().getQuantity(), productDto.getQuantity()),
                () -> assertEquals(testProductDto().getCategoryId(), productDto.getCategoryId())
        );
    }

    @Test
    void getProductByIdTest() {
        Product testProduct = productRepository.save(testProduct());
        ProductDto productDto = testRestTemplate.getForObject(String.format(getProductById, testProduct.getId()), ProductDto.class);
        assertAll(
                () -> assertEquals(testProductDto().getName(), productDto.getName()),
                () -> assertEquals(testProductDto().getDescription(), productDto.getDescription()),
                () -> assertEquals(testProductDto().getQuantity(), productDto.getQuantity()),
                () -> assertEquals(testProductDto().getCategoryId(), productDto.getCategoryId())
        );
    }

    @Test
    void updateProductTest() {
        Product testProduct = testProduct();
        testProduct.setCategory(testCategory);
        testProduct = productRepository.save(testProduct);
        testProduct.setName("updated name");
        ProductDto testProductDto = ProductDto.builder()
                .id(testProduct.getId())
                .name(testProduct.getName())
                .description(testProduct.getDescription())
                .quantity(testProduct.getQuantity())
                .categoryId(testProduct.getCategory().getId())
                .build();
        System.out.println(testProduct);
        System.out.println(productRepository.existsById(testProduct.getId()));
        ProductDto productDto = testRestTemplate.exchange(
                String.format(updateProduct, testProduct.getId()),
                HttpMethod.PUT,
                new HttpEntity<>(testProductDto),
                ProductDto.class).getBody();
        System.out.println(productDto);
        assertEquals(testProduct.getName(), productDto.getName());
        assertEquals(testProduct.getDescription(), productDto.getDescription());
        assertEquals(testProduct.getQuantity(), productDto.getQuantity());
        assertEquals(testProduct.getCategory().getId(), productDto.getCategoryId());
    }

    @Test
    void deleteProduct() {
      Product product = productRepository.save(testProduct());
      testRestTemplate.delete(String.format(deleteProduct,product.getId()));
      assertFalse(productRepository.existsById(product.getId()));
    }
}
