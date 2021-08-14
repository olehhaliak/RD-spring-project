package my.flick.rd.springproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.flick.rd.springproject.Testconfig;
import my.flick.rd.springproject.controller.assembler.CategoryAssembler;
import my.flick.rd.springproject.controller.assembler.OrderAssembler;
import my.flick.rd.springproject.controller.model.CategoryModel;
import my.flick.rd.springproject.controller.model.OrderModel;
import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.dto.OrderDto;
import my.flick.rd.springproject.exception.CategoryNotFoundException;
import my.flick.rd.springproject.exception.UserNotFoundException;
import my.flick.rd.springproject.model.enums.Status;
import my.flick.rd.springproject.service.CategoryService;
import my.flick.rd.springproject.service.OrderService;
import my.flick.rd.springproject.test.utils.ObjectToJsonConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static my.flick.rd.springproject.test.utils.CategoryTestData.*;
import static my.flick.rd.springproject.test.utils.OrderItemTestData.ITEM_PRODUCT_ID;
import static my.flick.rd.springproject.test.utils.OrderItemTestData.ITEM_QUANTITY;
import static my.flick.rd.springproject.test.utils.OrderTestData.*;
import static my.flick.rd.springproject.test.utils.UserTestData.USER_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@Import(Testconfig.class)
class OrderControllerTest {
    private static final String GET_ALL_ORDERS = linkTo(methodOn(OrderController.class).getAllOrders()).toString();
    private static final String GET_CURRENT_USER_ORDERS = linkTo(methodOn(OrderController.class).getCurrentUserOrders()).toString();
    private static final String GET_USER_ORDERS = linkTo(methodOn(OrderController.class).getUserOrders(USER_ID)).toString();
    private static final String CHANGE_ORDER_STATUS = linkTo(methodOn(OrderController.class).changeOrderStatus(ORDER_ID, null)).toString();

    @Autowired
    ObjectToJsonConverter jsonConverter;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderService orderService;
    @MockBean
    OrderAssembler orderAssembler;

    @Test
    void getAllOrdersTest() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of(testOrderDto()));
        when(orderAssembler.toModel(testOrderDto())).thenReturn(new OrderModel(testOrderDto()));
        mockMvc.perform(get(GET_ALL_ORDERS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(ORDER_ID))
                .andExpect(jsonPath("$.[0].customerId").value(USER_ID))
                .andExpect(jsonPath("$.[0].status").value(ORDER_STATUS.toString()))
                .andExpect(jsonPath("$.[0].creationTime").isNotEmpty())
                .andExpect(jsonPath("$.[0].updateTime").isNotEmpty())
                .andExpect(jsonPath("$.[0].items").isArray())
                .andExpect(jsonPath("$.[0].items.[0].productId").value(ITEM_PRODUCT_ID))
                .andExpect(jsonPath("$.[0].items.[0].quantity").value(ITEM_QUANTITY));
    }

    @Test
    void getCurrentUserOrdersTest() throws Exception {
        when(orderService.getCurrentUserOrders()).thenReturn(List.of(testOrderDto()));
        when(orderAssembler.toModel(testOrderDto())).thenReturn(new OrderModel(testOrderDto()));
        mockMvc.perform(get(GET_CURRENT_USER_ORDERS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(ORDER_ID))
                .andExpect(jsonPath("$.[0].customerId").value(USER_ID))
                .andExpect(jsonPath("$.[0].status").value(ORDER_STATUS.toString()))
                .andExpect(jsonPath("$.[0].creationTime").isNotEmpty())
                .andExpect(jsonPath("$.[0].updateTime").isNotEmpty())
                .andExpect(jsonPath("$.[0].items").isArray())
                .andExpect(jsonPath("$.[0].items.[0].productId").value(ITEM_PRODUCT_ID))
                .andExpect(jsonPath("$.[0].items.[0].quantity").value(ITEM_QUANTITY));
    }

    @Test
    void getUserOrdersTest() throws Exception {
        when(orderService.getUserOrders(USER_ID)).thenReturn(List.of(testOrderDto()));
        when(orderAssembler.toModel(testOrderDto())).thenReturn(new OrderModel(testOrderDto()));
        mockMvc.perform(get(GET_USER_ORDERS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(ORDER_ID))
                .andExpect(jsonPath("$.[0].customerId").value(USER_ID))
                .andExpect(jsonPath("$.[0].status").value(ORDER_STATUS.toString()))
                .andExpect(jsonPath("$.[0].creationTime").isNotEmpty())
                .andExpect(jsonPath("$.[0].updateTime").isNotEmpty())
                .andExpect(jsonPath("$.[0].items").isArray())
                .andExpect(jsonPath("$.[0].items.[0].productId").value(ITEM_PRODUCT_ID))
                .andExpect(jsonPath("$.[0].items.[0].quantity").value(ITEM_QUANTITY));
    }

    @Test
    void changeOrderStatusTest() throws Exception {
        OrderDto testOrderDto = testOrderDto();
        testOrderDto.setStatus(Status.PAID);
        when(orderService.changeOrderStatus(ORDER_ID, Status.PAID)).thenReturn(testOrderDto);
        when(orderAssembler.toModel(testOrderDto)).thenReturn(new OrderModel(testOrderDto));
        mockMvc.perform(patch(CHANGE_ORDER_STATUS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(Status.PAID))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ORDER_ID))
                .andExpect(jsonPath("$.customerId").value(USER_ID))
                .andExpect(jsonPath("$.status").value(Status.PAID.toString()))
                .andExpect(jsonPath("$.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.updateTime").isNotEmpty())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.[0].productId").value(ITEM_PRODUCT_ID))
                .andExpect(jsonPath("$.items.[0].quantity").value(ITEM_QUANTITY));
    }
}