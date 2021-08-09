package my.flick.rd.springproject.controller;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.api.OrderApi;
import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.model.enums.Status;
import my.flick.rd.springproject.service.OrderService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {
    private final OrderService orderService;

    @Override
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }


    @Override
    public List<OrderDto> getCurrentUserOrders() {
        return orderService.getCurrentUserOrders();
    }

    @Override
    public List<OrderDto> getUserOrders(long userId) {
        return orderService.getUserOrders(userId);
    }

    @Override
    public OrderDto changeOrderStatus(long id, Status status) {
        return orderService.changeOrderStatus(id,status);
    }
}
