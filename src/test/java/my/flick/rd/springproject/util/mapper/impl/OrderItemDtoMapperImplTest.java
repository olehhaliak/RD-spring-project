package my.flick.rd.springproject.util.mapper.impl;

import my.flick.rd.springproject.dto.OrderItemDto;
import my.flick.rd.springproject.model.OrderItem;
import org.junit.jupiter.api.Test;

import static my.flick.rd.springproject.test.utils.OrderItemTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemDtoMapperImplTest {
   OrderItemDtoMapperImpl itemDtoMapper = new OrderItemDtoMapperImpl();

    @Test
    void mapToDtoTest() {
        OrderItemDto actualItemDto = itemDtoMapper.mapToDto(testOrderItem());
        assertAll(
                ()->assertEquals(ITEM_PRODUCT_ID,actualItemDto.getProductId()),
                ()->assertEquals(ITEM_QUANTITY,actualItemDto.getQuantity())
        );
    }

    @Test
    void maoToModelTest() {
        OrderItem actualItem = itemDtoMapper.mapToModel(testOrderItemDto());
        assertAll(
                ()->assertEquals(ITEM_PRODUCT_ID,actualItem.getProductId()),
                ()->assertEquals(ITEM_QUANTITY,actualItem.getQuantity())
        );
    }
}