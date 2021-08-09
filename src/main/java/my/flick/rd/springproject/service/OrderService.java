package my.flick.rd.springproject.service;


import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.model.OrderItem;

import java.util.List;
import java.util.Set;

public interface OrderService {
    OrderDto createOrder(Set<OrderItem> items);
    List<OrderDto> getAllOrders();
    List<OrderDto> getAllActiveOrders();
    List<OrderDto> getCurrentUserOrders();
    List<OrderDto> getUserOrders(long userId);
    OrderDto changeOrderStatus(OrderDto dto);
}
