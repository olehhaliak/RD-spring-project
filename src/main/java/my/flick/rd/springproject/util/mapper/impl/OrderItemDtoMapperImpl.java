package my.flick.rd.springproject.util.mapper.impl;

import my.flick.rd.springproject.dto.OrderItemDto;
import my.flick.rd.springproject.model.OrderItem;
import my.flick.rd.springproject.model.Product;
import my.flick.rd.springproject.util.mapper.OrderItemDtoMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderItemDtoMapperImpl implements OrderItemDtoMapper {

    @Override
    public OrderItem mapToModel(OrderItemDto dto) {
        return OrderItem.builder()
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .build();
    }

    @Override
    public OrderItemDto mapToDto(OrderItem model) {
        return new OrderItemDto(model.getProductId(),model.getQuantity());
    }
}
