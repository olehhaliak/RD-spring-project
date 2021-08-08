package my.flick.rd.springproject.api;

import my.flick.rd.springproject.controller.model.OrderItemModel;
import my.flick.rd.springproject.dto.OrderItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/cart")
public interface CartApi {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    List<OrderItemModel> saveItem(@RequestBody OrderItemDto itemDto);

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    List<OrderItemModel> deleteById(@PathVariable long productId);

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> clear();

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<OrderItemModel> getItemsFromCart();


}
