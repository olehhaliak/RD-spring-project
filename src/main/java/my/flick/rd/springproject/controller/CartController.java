package my.flick.rd.springproject.controller;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.api.CartApi;
import my.flick.rd.springproject.controller.assembler.CartAssembler;
import my.flick.rd.springproject.controller.model.OrderItemModel;
import my.flick.rd.springproject.dto.OrderItemDto;
import my.flick.rd.springproject.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CartController implements CartApi {
   private final CartService cartService;
   private final CartAssembler cartAssembler;
    @Override
    public List<OrderItemModel> saveItem(OrderItemDto itemDto) {
        System.out.println(itemDto.toString());
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
}
