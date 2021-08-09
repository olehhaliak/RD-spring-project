package my.flick.rd.springproject.api;

import io.swagger.annotations.Api;
import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.model.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RequestMapping("/api/v1/orders")
public interface OrderApi {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<OrderDto> getAllOrders();

    @GetMapping("/user/")
    @ResponseStatus(HttpStatus.OK)
    List<OrderDto> getCurrentUserOrders();

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<OrderDto> getUserOrders(@PathVariable("id") long userId);

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    OrderDto changeOrderStatus(@PathVariable("id") long id, @RequestBody Status status);
}
