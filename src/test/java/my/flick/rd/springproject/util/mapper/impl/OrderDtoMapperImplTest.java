package my.flick.rd.springproject.util.mapper.impl;

import my.flick.rd.springproject.model.Order;
import my.flick.rd.springproject.util.mapper.OrderItemDtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static my.flick.rd.springproject.test.utils.OrderItemTestData.testOrderItem;
import static my.flick.rd.springproject.test.utils.OrderItemTestData.testOrderItemDto;
import static my.flick.rd.springproject.test.utils.OrderTestData.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class OrderDtoMapperImplTest {
    @Mock
    OrderItemDtoMapper orderItemDtoMapper;

    @InjectMocks
    private OrderDtoMapperImpl dtoMapper;

    @Test
    void mapToDtoTest() {
        when(orderItemDtoMapper.mapToDto(any())).thenReturn(testOrderItemDto());
        assertThat(dtoMapper.mapToDto(testOrder()), is(equalTo(testOrderDto())));
    }

    @Test
    void mapToModelTest() {
        when(orderItemDtoMapper.mapToModel(any())).thenReturn(testOrderItem());
        Order actualOrder = dtoMapper.mapToModel(testOrderDto());
        assertAll(
                () -> assertEquals(ORDER_ID, actualOrder.getId()),
                () -> assertEquals(testOrder().getCustomer().getId(), actualOrder.getCustomer().getId()),
                () -> assertEquals(ORDER_STATUS, actualOrder.getStatus()),
                () -> assertTrue(actualOrder.getItems().contains(testOrderItem()))
        );
    }
}