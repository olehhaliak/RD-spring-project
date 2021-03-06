package my.flick.rd.springproject.service;

import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.dto.OrderItemDto;

import java.util.Arrays;
import java.util.Set;

public interface CartService {
    /**
     * adds new item to a cart;
     * updates item in the cart if it already exists
     *
     * @param itemDto
     * @return
     */
    Set<OrderItemDto> saveItem(OrderItemDto itemDto);

    Set<OrderItemDto> deleteItem(long productId);

    void clear();

    Set<OrderItemDto> getItems();

    OrderDto checkout();
}
