package my.flick.rd.springproject.test.utils;

import my.flick.rd.springproject.dto.OrderItemDto;
import my.flick.rd.springproject.model.Order;
import my.flick.rd.springproject.model.OrderItem;
import my.flick.rd.springproject.model.Product;

public class OrderItemTestData {
    public static final long ITEM_ID = 2;
    public static final long ITEM_PRODUCT_ID = 3;
    public static final long ITEM_ORDER_ID = 4;
    public static final int ITEM_QUANTITY = 5;

    public static OrderItem testOrderItem(){
        return OrderItem.builder()
                .id(ITEM_ID)
                .productId(ITEM_PRODUCT_ID)
                .quantity(ITEM_QUANTITY)
                .build();
    }

    public static OrderItemDto testOrderItemDto(){
        return new OrderItemDto(ITEM_PRODUCT_ID,ITEM_QUANTITY);
    }

}
