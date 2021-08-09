package my.flick.rd.springproject.controller.assembler;

import my.flick.rd.springproject.controller.OrderController;
import my.flick.rd.springproject.controller.model.OrderModel;
import my.flick.rd.springproject.dto.OrderDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component

public class OrderAssembler extends RepresentationModelAssemblerSupport<OrderDto, OrderModel> {
    public OrderAssembler() {
        super(OrderController.class, OrderModel.class);
    }

    @Override
    public OrderModel toModel(OrderDto entity) {
        OrderModel model = new OrderModel(entity);
        model.add(linkTo(methodOn(OrderController.class).changeOrderStatus(entity.getId(), null)).withRel("change status"));
        return model;
    }
}
