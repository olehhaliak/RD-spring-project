package my.flick.rd.springproject.service.impl;

import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.exception.*;
import my.flick.rd.springproject.model.Order;
import my.flick.rd.springproject.model.User;
import my.flick.rd.springproject.model.enums.Role;
import my.flick.rd.springproject.model.enums.Status;
import my.flick.rd.springproject.repository.OrderRepository;
import my.flick.rd.springproject.service.AuthService;
import my.flick.rd.springproject.service.ProductService;
import my.flick.rd.springproject.util.mapper.OrderDtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static my.flick.rd.springproject.test.utils.OrderItemTestData.testOrderItem;
import static my.flick.rd.springproject.test.utils.OrderItemTestData.testOrderItemDto;
import static my.flick.rd.springproject.test.utils.OrderTestData.*;
import static my.flick.rd.springproject.test.utils.UserTestData.USER_ID;
import static my.flick.rd.springproject.test.utils.UserTestData.testUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    ProductService productService;
    @Mock
    AuthService authService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderDtoMapper dtoMapper;
    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void createOrderTest() {
        User testUser = testUser();
        testUser.setRole(Role.CUSTOMER);
        when(authService.getCustomer()).thenReturn(testUser);
        when(productService.exists(anyLong())).thenReturn(true);
        when(orderRepository.save(Mockito.any())).thenReturn(testOrder());
        when(dtoMapper.mapToDto(testOrder())).thenReturn(testOrderDto());
        OrderDto actualOrder = orderService.createOrder(Set.of(testOrderItem()));
        assertAll(
                () -> assertEquals(Status.REGISTERED, actualOrder.getStatus()),
                () -> assertNotNull(actualOrder.getCreationTime()),
                () -> assertNotNull(actualOrder.getUpdateTime()),
                () -> assertNotSame(0, actualOrder.getId()),
                () -> assertEquals(testOrderDto().getItems(), actualOrder.getItems())
        );
    }

    @Test
    void createOrder_EmptySetTest() {
        assertThrows(CartIsEmptyException.class, () -> orderService.createOrder(new HashSet<>()));
    }

    @Test
    void createOrder_NotExistingProductTest() {
        when(productService.exists(anyLong())).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> orderService.createOrder(Set.of(testOrderItem())));
    }

    @Test
    void createOrder_NotAuthenticatedTest() {
        when(productService.exists(anyLong())).thenReturn(true);
        when(authService.getCustomer()).thenThrow(UserNotAuthentificatedException.class);
        assertThrows(UserNotAuthentificatedException.class, () -> orderService.createOrder(Set.of(testOrderItem())));
    }

    @Test
    void createOrder_UserIsNotCustomerTest() {
        when(productService.exists(anyLong())).thenReturn(true);
        when(authService.getCustomer()).thenThrow(UserIsNotCustomerException.class);
        assertThrows(UserIsNotCustomerException.class, () -> orderService.createOrder(Set.of(testOrderItem())));
    }

    @Test
    void getAllOrdersTest() {
        when(orderRepository.findAll()).thenReturn(List.of(testOrder()));
        when(dtoMapper.mapToDto(testOrder())).thenReturn(testOrderDto());
        assertThat(orderService.getAllOrders(), contains(testOrderDto()));
    }

    @Test
    void getCurrentUserOrdersTest() {
        when(orderRepository.findOrderByCustomer(USER_ID)).thenReturn(List.of(testOrder()));
        when(authService.getCustomer()).thenReturn(testUser());
        when(dtoMapper.mapToDto(testOrder())).thenReturn(testOrderDto());
        assertThat(orderService.getCurrentUserOrders(), contains(testOrderDto()));
    }

    @Test
    void getUserOrdersTest() {
        when(dtoMapper.mapToDto(testOrder())).thenReturn(testOrderDto());
        when(orderRepository.findOrderByCustomer(USER_ID)).thenReturn(List.of(testOrder()));
        when(dtoMapper.mapToDto(testOrder())).thenReturn(testOrderDto());
        assertThat(orderService.getUserOrders(USER_ID), contains(testOrderDto()));
    }

    @Test
    void changeOrderStatusTest() {
        Order testOrder = testOrder();
        Order testOrderWithNewStatus = testOrder();
        testOrderWithNewStatus.setStatus(Status.PAID);
        OrderDto testOrderDtoWithNewStatus = testOrderDto();
        testOrderDtoWithNewStatus.setStatus(Status.PAID);
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(testOrder()));
        when(orderRepository.save(testOrderWithNewStatus)).thenReturn(testOrderWithNewStatus);
        when(dtoMapper.mapToDto(testOrderWithNewStatus)).thenReturn(testOrderDtoWithNewStatus);
        assertEquals(testOrderDtoWithNewStatus, orderService.changeOrderStatus(ORDER_ID, Status.PAID));
    }

    @Test
    void changeOrderStatus_OrderNotExistsTest() {
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class,()->orderService.changeOrderStatus(ORDER_ID,Status.PAID));
    }
}