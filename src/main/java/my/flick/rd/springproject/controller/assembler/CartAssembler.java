package my.flick.rd.springproject.controller.assembler;

import my.flick.rd.springproject.controller.CartController;
import my.flick.rd.springproject.controller.model.OrderItemModel;
import my.flick.rd.springproject.dto.OrderItemDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CartAssembler extends RepresentationModelAssemblerSupport<OrderItemDto, OrderItemModel> {
    public CartAssembler() {
        super(CartController.class, OrderItemModel.class);
    }

    @Override
    public OrderItemModel toModel(OrderItemDto entity) {
       return new OrderItemModel(entity) ;
    }
}
