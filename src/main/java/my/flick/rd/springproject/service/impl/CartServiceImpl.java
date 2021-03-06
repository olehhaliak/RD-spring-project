package my.flick.rd.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.dto.OrderItemDto;
import my.flick.rd.springproject.exception.ProductNotFoundException;
import my.flick.rd.springproject.model.cart.impl.Cart;
import my.flick.rd.springproject.service.CartService;
import my.flick.rd.springproject.service.OrderService;
import my.flick.rd.springproject.service.ProductService;
import my.flick.rd.springproject.util.mapper.OrderItemDtoMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final Cart cart;
    private final OrderItemDtoMapper itemDtoMapper;
    private final ProductService productService;
    private final OrderService orderService;

    @Override
    public Set<OrderItemDto> getItems() {
        return cart.getItems().stream().map(itemDtoMapper::mapToDto).collect(Collectors.toSet());
    }

    @Override
    public Set<OrderItemDto> saveItem(OrderItemDto itemDto) {
        if (!productService.exists(itemDto.getProductId())) {
            throw new ProductNotFoundException("Product with such productId does not exists");
        }
        cart.saveItem(itemDtoMapper.mapToModel(itemDto));
        return cart.getItems().stream().map(itemDtoMapper::mapToDto).collect(Collectors.toSet());
    }

    @Override
    public Set<OrderItemDto> deleteItem(long productId) {
        cart.deleteItem(productId);
        return cart.getItems().stream().map(itemDtoMapper::mapToDto).collect(Collectors.toSet());
    }


    @Override
    public void clear() {
        cart.clear();
    }

    @Override
    public OrderDto checkout() {
        OrderDto createdOrderDto = orderService.createOrder(cart.getItems());
        clear();
        return createdOrderDto;
    }
}
