package my.flick.rd.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.flick.rd.springproject.dto.OrderDto;
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
        User customer = authService.getCustomer();
        checkForProductExistence(items);
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
    public List<OrderDto> getAllOrders() {
        return null;
    }

    @Override
    public List<OrderDto> getAllActiveOrders() {
        return null;
    }

    @Override
    public List<OrderDto> getCurrentUserOrders() {
        return null;
    }

    @Override
    public List<OrderDto> getUserOrders(long userId) {
        return null;
    }

    @Override
    public OrderDto changeOrderStatus(OrderDto dto) {
        return null;
    }
}
