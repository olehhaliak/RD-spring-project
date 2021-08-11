package my.flick.rd.springproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.flick.rd.springproject.api.ProductApi;
import my.flick.rd.springproject.controller.assembler.ProductAssembler;
import my.flick.rd.springproject.controller.model.ProductModel;
import my.flick.rd.springproject.exception.ProductNotFoundException;
import my.flick.rd.springproject.service.ProductService;
import my.flick.rd.springproject.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static my.flick.rd.springproject.test.utils.ProductTestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)

@WebMvcTest
@ContextConfiguration(
        classes = AuthServiceImpl.class
)
class ProductControllerTest {

    private static final String GET_BY_ID_LINK = WebMvcLinkBuilder.linkTo(methodOn(ProductApi.class)
            .getProduct(PRODUCT_ID)).toString();
    private static final String GET_All_LINK = WebMvcLinkBuilder.linkTo(methodOn(ProductApi.class)
            .getProduct(null)).toString();
    private static final String ADD_LINK = WebMvcLinkBuilder.linkTo(methodOn(ProductApi.class)
            .addProduct(PRODUCT_DTO)).toString();
    private static final String UPDATE_LINK = WebMvcLinkBuilder.linkTo(methodOn(ProductApi.class)
            .updateProduct(PRODUCT_ID, null)).toString();
    private static final String DELETE_LINK = WebMvcLinkBuilder.linkTo(methodOn(ProductApi.class)
            .deleteProduct(PRODUCT_ID)).toString();
    @Autowired
    private ObjectMapper objectMapper;
@SpyBean
private AuthServiceImpl authService;
    @MockBean
    private ProductAssembler assembler;

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProductByIdTest() throws Exception {
        when(productService.getProductById(PRODUCT_ID)).thenReturn(PRODUCT_DTO);
        when(assembler.toModel(PRODUCT_DTO)).thenReturn(new ProductModel(PRODUCT_DTO));
        mockMvc.perform(get(GET_BY_ID_LINK))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id").value(PRODUCT_ID));
    }

    @Test
    void getProductByIdNotExistTest() throws Exception {
        when(productService.getProductById(PRODUCT_ID)).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(get(GET_BY_ID_LINK))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }


    @Test
    void getAllProductsTest() throws Exception {
        when(productService.getProducts(null)).thenReturn(List.of(PRODUCT_DTO));
        when(assembler.toModel(PRODUCT_DTO)).thenReturn(new ProductModel(PRODUCT_DTO));
        mockMvc.perform(get(GET_All_LINK))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(PRODUCT_ID));
    }

    @Test
    void addProductTest() throws Exception {
        when(productService.addProduct(any())).thenReturn(PRODUCT_DTO);
        when(assembler.toModel(any())).thenReturn(new ProductModel(PRODUCT_DTO));
        mockMvc.perform(post(ADD_LINK)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(PRODUCT_DTO)))
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.id").value(PRODUCT_ID));

    }

    @Test
    void updateProductTest() throws Exception {
        when(productService.updateProduct(anyInt(),any())).thenReturn(PRODUCT_DTO);
        when(assembler.toModel(any())).thenReturn(new ProductModel(PRODUCT_DTO));
        mockMvc.perform(put(UPDATE_LINK)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(PRODUCT_DTO)))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id").value(PRODUCT_ID));
    }

    @Test
    void updateProductNotExistsTest() throws Exception {
        when(productService.updateProduct(anyInt(),any())).thenThrow(new ProductNotFoundException("exception"));
        when(assembler.toModel(any())).thenThrow(new ProductNotFoundException("exception"));
        mockMvc.perform(put(UPDATE_LINK)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(PRODUCT_DTO)))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
//        verify(assembler).toModel(PRODUCT_DTO);
    }



    @Test
    void deleteProductTest() throws Exception {
        mockMvc.perform(delete(DELETE_LINK))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void deleteProductNotExistsTest() throws Exception {
        doThrow(new ProductNotFoundException("")).when(productService).deleteProduct(PRODUCT_ID);
        mockMvc.perform(delete(DELETE_LINK))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }


    private  String asJsonString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}