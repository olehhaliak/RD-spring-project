package my.flick.rd.springproject.controller.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import my.flick.rd.springproject.dto.OrderDto;
import org.springframework.hateoas.RepresentationModel;

public class OrderModel extends RepresentationModel<OrderModel> {
    public OrderModel(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    @JsonUnwrapped
    OrderDto orderDto;
}
