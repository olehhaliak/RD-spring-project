package my.flick.rd.springproject.test.utils;

import my.flick.rd.springproject.dto.OrderItemDto;
import my.flick.rd.springproject.model.OrderItem;
import my.flick.rd.springproject.model.Product;

public class OrderItemTestData {
    public static final long ITEM_ID = 2;
    public static final long ITEM_PRODUCT_ID = 3;
    public static final long ITEM_ORDER_ID = 4;
    public static final int ITEM_QUANTITY = 5;

    public static OrderItem testOrderItem(){
        Product product = Product.builder().id(ITEM_PRODUCT_ID).build();
        return OrderItem.builder()
                .id(ITEM_ID)
                .product(product)
                .orderId(ITEM_ORDER_ID)
                .quantity(ITEM_QUANTITY)
                .build();
    }

    public static OrderItemDto testOrderItemDto(){
        return new OrderItemDto(ITEM_PRODUCT_ID,ITEM_QUANTITY);
    }

}
