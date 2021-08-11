package my.flick.rd.springproject.service.impl;

import my.flick.rd.springproject.exception.ProductNotFoundException;
import my.flick.rd.springproject.model.Cart;
import my.flick.rd.springproject.model.OrderItem;
import my.flick.rd.springproject.service.OrderService;
import my.flick.rd.springproject.service.ProductService;
import my.flick.rd.springproject.util.mapper.OrderItemDtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static my.flick.rd.springproject.test.utils.OrderItemTestData.*;
import static my.flick.rd.springproject.test.utils.OrderTestData.testOrderDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
    @Mock
    Cart cart;
    @Mock
    OrderItemDtoMapper orderItemDtoMapper;
    @Mock
    OrderService orderService;
    @Mock
    ProductService productService;
    @InjectMocks
    CartServiceImpl cartService;

    @Test
    void saveItemTest() {
        when(productService.exists(ITEM_PRODUCT_ID)).thenReturn(true);
        when(cart.getItems()).thenReturn(Set.of(testOrderItem()));
        when(orderItemDtoMapper.mapToDto(any())).thenReturn(testOrderItemDto());
        when(orderItemDtoMapper.mapToModel(any())).thenReturn(testOrderItem());
        assertThat(cartService.saveItem(testOrderItemDto()), contains(testOrderItemDto()));
        verify(cart).saveItem(any());
    }

    @Test
    void saveItem_ProductNotExistsTest() {
        when(productService.exists(ITEM_PRODUCT_ID)).thenReturn(false);
        assertThrows(ProductNotFoundException.class,()->cartService.saveItem(testOrderItemDto()));
    }

    @Test
    void deleteItemTest() {
        when(cart.getItems()).thenReturn(Set.of(testOrderItem()));
        when(orderItemDtoMapper.mapToDto(any())).thenReturn(testOrderItemDto());
        assertThat(cartService.deleteItem(ITEM_PRODUCT_ID), contains(testOrderItemDto()));
        verify(cart).deleteItem(ITEM_PRODUCT_ID);
    }

    @Test
    void popItemsTest() {
        when(cart.pop()).thenReturn(Set.of(testOrderItem()));
        when(orderItemDtoMapper.mapToDto(any())).thenReturn(testOrderItemDto());
        assertThat(cartService.popItems(), contains(testOrderItemDto()));
    }

    @Test
    void clearTest() {
       cartService.clear();
       verify(cart).clear();
    }

    @Test
    void getItemsTest() {
        when(cart.getItems()).thenReturn(Set.of(testOrderItem()));
        when(orderItemDtoMapper.mapToDto(any())).thenReturn(testOrderItemDto());
        assertThat(cartService.getItems(), contains(testOrderItemDto()));
    }

    @Test
    void checkoutTest() {
        Set<OrderItem> testItems = Set.of(testOrderItem());
        when(cart.getItems()).thenReturn(testItems);
        when(orderService.createOrder(testItems)).thenReturn(testOrderDto());
        assertThat(cartService.checkout(),is(equalTo(testOrderDto())));
    }
}