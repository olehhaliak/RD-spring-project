package my.flick.rd.springproject.test.utils;

import my.flick.rd.springproject.dto.ProductDto;
import my.flick.rd.springproject.model.Category;
import my.flick.rd.springproject.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductTestData {
    public static final long PRODUCT_ID = 3;
    public static final String PRODUCT_NAME = "Lenovo Yoga";
    public static final BigDecimal PRODUCT_PRICE = new BigDecimal(449);
    public static final String PRODUCT_DESCRIPTION = "laptop-transformer";
    public static final int PRODUCT_QUANTITY = 7;
    public static final long PRODUCT_CATEGORY_ID = 2;
    public static final Category PRODUCT_CATEGORY = Category.builder().id(PRODUCT_CATEGORY_ID).build();
    public static final LocalDateTime CREATION_TIME = LocalDateTime.now();
    public static final LocalDateTime UPDATE_TIME = LocalDateTime.now();

    public static Product testProduct() {
        return Product.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .price(PRODUCT_PRICE)
                .description(PRODUCT_DESCRIPTION)
                .quantity(PRODUCT_QUANTITY)
                .category(PRODUCT_CATEGORY)
                .creationTime(CREATION_TIME)
                .updateTime(UPDATE_TIME)
                .build();
    }

    public static ProductDto testProductDto() {
        return ProductDto.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .price(PRODUCT_PRICE)
                .description(PRODUCT_DESCRIPTION)
                .quantity(PRODUCT_QUANTITY)
                .categoryId(PRODUCT_CATEGORY_ID)
                .creationTime(CREATION_TIME)
                .updateTime(UPDATE_TIME)
                .build();
    }
}
