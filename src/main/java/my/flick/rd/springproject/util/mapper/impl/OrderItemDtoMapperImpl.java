package my.flick.rd.springproject.util.mapper.impl;

import my.flick.rd.springproject.dto.OrderItemDto;
import my.flick.rd.springproject.model.OrderItem;
import my.flick.rd.springproject.model.Product;
import my.flick.rd.springproject.util.mapper.OrderItemDtoMapper;

public class OrderItemDtoMapperImpl implements OrderItemDtoMapper {

    @Override
    public OrderItem mapToModel(OrderItemDto dto) {
        return OrderItem.builder()
                .product(Product.builder().id(dto.getProductId()).build())
                .quantity(dto.getQuantity())
                .build();
    }

    @Override
    public OrderItemDto mapToDto(OrderItem model) {
        return new OrderItemDto(model.getProduct().getId(),model.getQuantity());
    }
}
