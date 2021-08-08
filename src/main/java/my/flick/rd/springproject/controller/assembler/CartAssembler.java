package my.flick.rd.springproject.controller.assembler;

import my.flick.rd.springproject.controller.CartController;
import my.flick.rd.springproject.controller.model.OrderItemModel;
import my.flick.rd.springproject.dto.OrderItemDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartAssembler extends RepresentationModelAssemblerSupport<OrderItemDto, OrderItemModel> {
    public CartAssembler() {
        super(CartController.class, OrderItemModel.class);
    }

    @Override
    public OrderItemModel toModel(OrderItemDto entity) {
        OrderItemModel model = new OrderItemModel(entity);
        model.add(linkTo(methodOn(CartController.class).getItemsFromCart()).withRel("getAll"));
        model.add(linkTo(methodOn(CartController.class).saveItem(null)).withRel("save"));
        model.add(linkTo(methodOn(CartController.class).deleteById(entity.getProductId())).withRel("delete"));
        model.add(linkTo(methodOn(CartController.class).clear()).withRel("clearAll"));
        return model;
    }
}
