package my.flick.rd.springproject.controller.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import my.flick.rd.springproject.dto.OrderItemDto;
import org.springframework.hateoas.RepresentationModel;

public class OrderItemModel extends RepresentationModel<OrderItemModel> {
    @JsonUnwrapped
    OrderItemDto itemDto;

    public OrderItemModel(OrderItemDto itemDto) {
        this.itemDto = itemDto;
    }
}
