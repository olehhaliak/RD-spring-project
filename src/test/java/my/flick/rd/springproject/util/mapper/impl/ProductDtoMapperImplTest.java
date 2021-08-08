package my.flick.rd.springproject.util.mapper.impl;
import static my.flick.rd.springproject.test.utils.ProductTestData.*;
import my.flick.rd.springproject.dto.ProductDto;
import my.flick.rd.springproject.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoMapperImplTest {

    ProductDtoMapperImpl productDtoMapper = new ProductDtoMapperImpl();

    @Test
    void mapToDtoTest() {


       ProductDto productDto = productDtoMapper.mapToDto(PRODUCT);
       assertAll(
               ()->assertEquals(PRODUCT_ID,productDto.getId()),
               ()->assertEquals(PRODUCT_NAME,productDto.getName()),
               ()->assertEquals(PRODUCT_PRICE,productDto.getPrice()),
               ()->assertEquals(PRODUCT_DESCRIPTION,productDto.getDescription()),
               ()->assertEquals(PRODUCT_QUANTITY,productDto.getQuantity()),
               ()->assertEquals(CREATION_TIME,productDto.getCreationTime()),
               ()->assertEquals(UPDATE_TIME,productDto.getUpdateTime())
       );
    }

    @Test
    void mapToModelTest() {
        Product product = productDtoMapper.mapToModel(PRODUCT_DTO);
        assertAll(
                ()->assertEquals(PRODUCT_ID,product.getId()),
                ()->assertEquals(PRODUCT_NAME,product.getName()),
                ()->assertEquals(PRODUCT_PRICE,product.getPrice()),
                ()->assertEquals(PRODUCT_DESCRIPTION,product.getDescription()),
                ()->assertEquals(PRODUCT_QUANTITY,product.getQuantity()),
                ()->assertEquals(CREATION_TIME,product.getCreationTime()),
                ()->assertEquals(UPDATE_TIME,product.getUpdateTime())
        );
    }

}