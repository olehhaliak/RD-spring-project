package my.flick.rd.springproject.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import my.flick.rd.springproject.controller.model.OrderItemModel;
import my.flick.rd.springproject.dto.OrderItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/cart")
@Api(tags = "Cart management api")
public interface CartApi {

    @ApiOperation("adds or updates item in cart")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    List<OrderItemModel> saveItem(@RequestBody OrderItemDto itemDto);

    @ApiOperation("deletes item with specified id ")
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    List<OrderItemModel> deleteById(@PathVariable long productId);

    @ApiOperation("clears cart")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> clear();

    @ApiOperation("returns all items from cart")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<OrderItemModel> getItemsFromCart();


}
