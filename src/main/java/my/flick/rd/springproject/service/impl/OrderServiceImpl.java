package my.flick.rd.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.flick.rd.springproject.annotation.RequireAdminPrivileges;
import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.exception.CartIsEmptyException;
import my.flick.rd.springproject.exception.OrderItemNotFoundException;
import my.flick.rd.springproject.exception.OrderNotFoundException;
import my.flick.rd.springproject.exception.ProductNotFoundException;
import my.flick.rd.springproject.model.Order;
import my.flick.rd.springproject.model.OrderItem;
import my.flick.rd.springproject.model.User;
import my.flick.rd.springproject.model.enums.Status;
import my.flick.rd.springproject.repository.OrderRepository;
import my.flick.rd.springproject.service.AuthService;
import my.flick.rd.springproject.service.OrderService;
import my.flick.rd.springproject.service.ProductService;
import my.flick.rd.springproject.util.mapper.OrderDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final ProductService productService;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderDtoMapper;

    @Override
    @Transactional
    public OrderDto createOrder(Set<OrderItem> items) {
        if(items.isEmpty()){
           throw new CartIsEmptyException("Cart is empty,can not create an order");
        }
        checkForProductExistence(items);
        User customer = authService.getCustomer();
        Order order = Order.builder()
                .customer(customer)
                .status(Status.REGISTERED)
                .items(items)
                .build();
        order = orderRepository.save(order);
        return orderDtoMapper.mapToDto(order);
    }

    private void checkForProductExistence(Set<OrderItem> items) {
        for (OrderItem item : items) {
            if (!productService.exists(item.getProductId())) {
                throw new ProductNotFoundException("Product with id '" + item.getProductId() + "' does not exists");
            }
        }
    }


    @Override
    @RequireAdminPrivileges
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(orderDtoMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getCurrentUserOrders() {
        return getUserOrders(authService.getCustomer().getId());
    }

    @Override
    @RequireAdminPrivileges
    public List<OrderDto> getUserOrders(long userId) {
        return orderRepository.findOrderByCustomer(userId).stream()
                .map(orderDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @RequireAdminPrivileges
    @Transactional
    public OrderDto changeOrderStatus(long id, Status status) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(()-> new OrderNotFoundException("Order with such id does not exists"));
        order.setStatus(status);
        order = orderRepository.save(order);
        return orderDtoMapper.mapToDto(order);
    }
}
