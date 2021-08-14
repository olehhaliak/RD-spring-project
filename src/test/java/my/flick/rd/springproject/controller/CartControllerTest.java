package my.flick.rd.springproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import my.flick.rd.springproject.Testconfig;
import my.flick.rd.springproject.controller.assembler.CartAssembler;
import my.flick.rd.springproject.controller.model.OrderItemModel;
import my.flick.rd.springproject.dto.OrderItemDto;
import my.flick.rd.springproject.exception.OrderItemNotFoundException;
import my.flick.rd.springproject.exception.ProductNotFoundException;
import my.flick.rd.springproject.service.CartService;
import my.flick.rd.springproject.test.utils.ObjectToJsonConverter;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static my.flick.rd.springproject.test.utils.CategoryTestData.CATEGORY_DTO;
import static my.flick.rd.springproject.test.utils.CategoryTestData.CATEGORY_ID;
import static my.flick.rd.springproject.test.utils.OrderItemTestData.*;
import static my.flick.rd.springproject.test.utils.OrderTestData.*;
import static my.flick.rd.springproject.test.utils.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartController.class)
@Import(Testconfig.class)
class CartControllerTest {
    private static final String SAVE_ITEM = linkTo(methodOn(CartController.class).saveItem(testOrderItemDto())).toString();
    private static final String DELETE_BY_ID = linkTo(methodOn(CartController.class).deleteById(ITEM_ID)).toString();
    private static final String CLEAR = linkTo(methodOn(CartController.class).clear()).toString();
    private static final String GET_ITEMS_FROM_CART = linkTo(methodOn(CartController.class).getItemsFromCart()).toString();
    private static final String CHECKOUT = linkTo(methodOn(CartController.class).checkout()).toString();

    @Autowired
    ObjectToJsonConverter jsonConverter;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CartService cartService;
    @MockBean
    CartAssembler cartAssembler;

    @Test
    void saveItemTest() throws Exception {
        when(cartService.saveItem(testOrderItemDto())).thenReturn(Set.of(testOrderItemDto()));
        when(cartAssembler.toModel(testOrderItemDto())).thenReturn(new OrderItemModel(testOrderItemDto()));
        mockMvc.perform(post(SAVE_ITEM)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testOrderItemDto()))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].productId").value(ITEM_PRODUCT_ID))
                .andExpect(jsonPath("$.[0].quantity").value(ITEM_QUANTITY));
    }

    @Test
    void saveItem_ZeroProductIdTest() throws Exception {
        OrderItemDto testOrderItemDto = testOrderItemDto();
        testOrderItemDto.setProductId(0);
        mockMvc.perform(post(SAVE_ITEM)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testOrderItemDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void saveItem_NegativeProductIdTest() throws Exception {
        OrderItemDto testOrderItemDto = testOrderItemDto();
        testOrderItemDto.setProductId(0);
        mockMvc.perform(post(SAVE_ITEM)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testOrderItemDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void saveItem_ZeroQuantityIdTest() throws Exception {
        OrderItemDto testOrderItemDto = testOrderItemDto();
        testOrderItemDto.setQuantity(0);
        mockMvc.perform(post(SAVE_ITEM)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testOrderItemDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void saveItem_NegativeQuantityIdTest() throws Exception {
        OrderItemDto testOrderItemDto = testOrderItemDto();
        testOrderItemDto.setQuantity(-1);
        mockMvc.perform(post(SAVE_ITEM)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testOrderItemDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void saveItem_ProductNotExistsTest() throws Exception {
        when(cartService.saveItem(testOrderItemDto())).thenThrow(ProductNotFoundException.class);
        OrderItemDto testOrderItemDto = testOrderItemDto();
        testOrderItemDto.setQuantity(-1);
        mockMvc.perform(post(SAVE_ITEM)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testOrderItemDto()))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void deleteByIdTest() throws Exception {
        when(cartService.deleteItem(ITEM_ID)).thenReturn(Set.of(testOrderItemDto()));
        when(cartAssembler.toModel(testOrderItemDto())).thenReturn(new OrderItemModel(testOrderItemDto()));
        mockMvc.perform(delete(DELETE_BY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testOrderItemDto()))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].productId").value(ITEM_PRODUCT_ID))
                .andExpect(jsonPath("$.[0].quantity").value(ITEM_QUANTITY));
    }

    @Test
    void deleteById_NotFoundTest() throws Exception {
        when(cartService.deleteItem(ITEM_ID)).thenThrow(OrderItemNotFoundException.class);
        mockMvc.perform(delete(DELETE_BY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testOrderItemDto()))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void clearTest() throws Exception {
        mockMvc.perform(delete(CLEAR))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void getItemsFromCartTest() throws Exception {
        when(cartService.getItems()).thenReturn(Set.of(testOrderItemDto()));
        when(cartAssembler.toModel(testOrderItemDto())).thenReturn(new OrderItemModel(testOrderItemDto()));
        mockMvc.perform(MockMvcRequestBuilders.get(GET_ITEMS_FROM_CART))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].productId").value(ITEM_PRODUCT_ID))
                .andExpect(jsonPath("$.[0].quantity").value(ITEM_QUANTITY));
    }

    @Test
    void checkoutTest() throws Exception {
        when(cartService.checkout()).thenReturn(testOrderDto());
        mockMvc.perform(post(CHECKOUT))
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.id").value(ORDER_ID))
                .andExpect(jsonPath("$.customerId").value(USER_ID))
                .andExpect(jsonPath("$.status").value(ORDER_STATUS.toString()))
                .andExpect(jsonPath("$.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.updateTime").isNotEmpty())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.[0].productId").value(ITEM_PRODUCT_ID))
                .andExpect(jsonPath("$.items.[0].quantity").value(ITEM_QUANTITY));
    }
}