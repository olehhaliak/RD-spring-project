package my.flick.rd.springproject.service;

import my.flick.rd.springproject.dto.OrderItemDto;

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

    Set<OrderItemDto> popItems();

    void clear();
    //Todo:add method for ceatingOrder
}
