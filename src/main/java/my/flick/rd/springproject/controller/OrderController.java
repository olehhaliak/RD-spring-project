package my.flick.rd.springproject.controller;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.api.OrderApi;
import my.flick.rd.springproject.controller.assembler.OrderAssembler;
import my.flick.rd.springproject.controller.model.OrderModel;
import my.flick.rd.springproject.model.enums.Status;
import my.flick.rd.springproject.service.OrderService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {
    private final OrderService orderService;
    private final OrderAssembler orderAssembler;

    @Override
    public List<OrderModel> getAllOrders() {
        return orderService.getAllOrders().stream().map(orderAssembler::toModel).collect(Collectors.toList());
    }


    @Override
    public List<OrderModel> getCurrentUserOrders() {
        return orderService.getCurrentUserOrders().stream().map(orderAssembler::toModel).collect(Collectors.toList());
    }

    @Override
    public List<OrderModel> getUserOrders(long userId) {
        return orderService.getUserOrders(userId).stream().map(orderAssembler::toModel).collect(Collectors.toList());
    }

    @Override
    public OrderModel changeOrderStatus(long id, Status status) {
        return orderAssembler.toModel(orderService.changeOrderStatus(id,status));
    }
}
