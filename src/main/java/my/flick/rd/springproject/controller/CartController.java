package my.flick.rd.springproject.controller;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.api.CartApi;
import my.flick.rd.springproject.controller.assembler.CartAssembler;
import my.flick.rd.springproject.controller.model.OrderItemModel;
import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.dto.OrderItemDto;
import my.flick.rd.springproject.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CartController implements CartApi {
   private final CartService cartService;
   private final CartAssembler cartAssembler;
    @Override
    public List<OrderItemModel> saveItem(@Valid OrderItemDto itemDto) {
       return cartService.saveItem(itemDto).stream().map(cartAssembler::toModel).collect(Collectors.toList());
    }

    @Override
    public List<OrderItemModel> deleteById(long productId) {
        return cartService.deleteItem(productId).stream().map(cartAssembler::toModel).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Void> clear() {
         cartService.clear();
       return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<OrderItemModel> getItemsFromCart() {
        return cartService.getItems().stream().map(cartAssembler::toModel).collect(Collectors.toList());
    }

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto checkout(){//Todo:add this to CartApi properly
        return cartService.checkout();
    }
}
