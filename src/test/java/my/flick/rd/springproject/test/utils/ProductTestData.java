package my.flick.rd.springproject.test.utils;

import my.flick.rd.springproject.dto.ProductDto;
import my.flick.rd.springproject.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductTestData {
    public static final long PRODUCT_ID = 3;
    public static final String PRODUCT_NAME = "Lenovo Yoga";
    public static final BigDecimal PRODUCT_PRICE = new BigDecimal(449);
    public static final String PRODUCT_DESCRIPTION = "laptop-transformer";
    public static final int PRODUCT_QUANTITY = 7;
    public static final LocalDateTime CREATION_TIME = LocalDateTime.now();
    public static final LocalDateTime UPDATE_TIME = LocalDateTime.now();
    public static final Product PRODUCT = Product.builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .price(PRODUCT_PRICE)
            .description(PRODUCT_DESCRIPTION)
            .quantity(PRODUCT_QUANTITY)
            .creationTime(CREATION_TIME)
            .updateTime(UPDATE_TIME)
            .build();
    public static final ProductDto PRODUCT_DTO = ProductDto.builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .price(PRODUCT_PRICE)
            .description(PRODUCT_DESCRIPTION)
            .quantity(PRODUCT_QUANTITY)
            .creationTime(CREATION_TIME)
            .updateTime(UPDATE_TIME)
            .build();
}
