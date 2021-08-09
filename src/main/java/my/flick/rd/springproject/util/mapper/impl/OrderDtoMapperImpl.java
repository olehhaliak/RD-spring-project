package my.flick.rd.springproject.util.mapper.impl;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.dto.OrderItemDto;
import my.flick.rd.springproject.model.Order;
import my.flick.rd.springproject.model.OrderItem;
import my.flick.rd.springproject.model.User;
import my.flick.rd.springproject.util.mapper.OrderDtoMapper;
import my.flick.rd.springproject.util.mapper.OrderItemDtoMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class OrderDtoMapperImpl implements OrderDtoMapper {
       private final OrderItemDtoMapper orderItemDtoMapper;
    @Override
    public Order mapToModel(OrderDto dto) {
        return Order.builder()
                .id(dto.getId())
                .customer(User.builder().id(dto.getCustomerId()).build())
                .status(dto.getStatus())
                .items(dto.getItems().stream().map(orderItemDtoMapper::mapToModel).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public OrderDto mapToDto(Order model) {
        return OrderDto.builder()
                .id(model.getId())
                .status(model.getStatus())
                .customerId(model.getCustomer().getId())
                .items(model.getItems().stream().map(orderItemDtoMapper::mapToDto).collect(Collectors.toList()))
                .creationTime(model.getCreationTime())
                .updateTime(model.getUpdateTime())
                .build();
    }
}
