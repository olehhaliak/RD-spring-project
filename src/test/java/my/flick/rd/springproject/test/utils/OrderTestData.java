package my.flick.rd.springproject.test.utils;

import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.model.Order;
import my.flick.rd.springproject.model.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static my.flick.rd.springproject.test.utils.OrderItemTestData.testOrderItem;
import static my.flick.rd.springproject.test.utils.OrderItemTestData.testOrderItemDto;
import static my.flick.rd.springproject.test.utils.UserTestData.USER_ID;
import static my.flick.rd.springproject.test.utils.UserTestData.testUser;

public class OrderTestData {
    public static final long ORDER_ID = 3;
    public static final Status ORDER_STATUS= Status.REGISTERED;
    public static final LocalDateTime ORDER_CREATION_TIME = LocalDateTime.now();
    public static final LocalDateTime ORDER_UPDATE_TIME = LocalDateTime.now();
    public static Order testOrder() {
        return Order.builder()
                .id(ORDER_ID)
                .customer(testUser())
                .status(ORDER_STATUS)
                .items(Set.of(testOrderItem()))
                .creationTime(ORDER_CREATION_TIME)
                .updateTime(ORDER_UPDATE_TIME)
                .build();
    }

    public static OrderDto testOrderDto(){
        return OrderDto.builder()
                .id(ORDER_ID)
                .status(ORDER_STATUS)
                .customerId(USER_ID)
                .items(List.of(testOrderItemDto()))
                .creationTime(ORDER_CREATION_TIME)
                .updateTime(ORDER_UPDATE_TIME)
                .build();
    }
}
