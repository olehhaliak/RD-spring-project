package my.flick.rd.springproject.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import my.flick.rd.springproject.controller.model.OrderModel;
import my.flick.rd.springproject.model.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Order management api")
@RequestMapping("/api/v1/orders")
public interface OrderApi {
    @ApiOperation("Get all orders")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<OrderModel> getAllOrders();

    @ApiOperation("get all orders of current user")
    @GetMapping("/user/")
    @ResponseStatus(HttpStatus.OK)
    List<OrderModel> getCurrentUserOrders();

    @ApiOperation("get all orders of user with id specified")
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<OrderModel> getUserOrders(@PathVariable("id") long userId);

    @ApiOperation("change order status")
    @PatchMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    OrderModel changeOrderStatus(@PathVariable("orderId") long id, @RequestBody Status status);
}
