package my.flick.rd.springproject.service;


import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.model.OrderItem;
import my.flick.rd.springproject.model.enums.Status;

import java.util.List;
import java.util.Set;

public interface OrderService {
    OrderDto createOrder(Set<OrderItem> items);
    List<OrderDto> getAllOrders();
    List<OrderDto> getCurrentUserOrders();
    List<OrderDto> getUserOrders(long userId);
    OrderDto changeOrderStatus(long id, Status status);
}
