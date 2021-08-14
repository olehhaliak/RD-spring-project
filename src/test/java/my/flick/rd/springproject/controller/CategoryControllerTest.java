package my.flick.rd.springproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.flick.rd.springproject.Testconfig;
import my.flick.rd.springproject.controller.assembler.CategoryAssembler;
import my.flick.rd.springproject.controller.model.CategoryModel;
import my.flick.rd.springproject.dto.CategoryDto;
import my.flick.rd.springproject.exception.CategoryNotFoundException;
import my.flick.rd.springproject.exception.UserNotFoundException;
import my.flick.rd.springproject.service.CategoryService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
@Import(Testconfig.class
)
class CategoryControllerTest {
    @Autowired
    private ObjectToJsonConverter jsonConverter;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private CategoryAssembler categoryAssembler;
    @Autowired
    MockMvc mockMvc;
    private static final String GET_BY_ID = linkTo(methodOn(CategoryController.class).getCategoryById(CATEGORY_ID)).toString();
    private static final String GET_SUBCATEGORIES = linkTo(methodOn(CategoryController.class).getSubcategories(CATEGORY_PARENT.getId())).toString();
    private static final String GET_ROOT_CATEGORIES = linkTo(methodOn(CategoryController.class).getRootCategories()).toString();
    private static final String ADD_CATEGORY = linkTo(methodOn(CategoryController.class).addCategory(null)).toString();
    private static final String UPDATE_CATEGORY = linkTo(methodOn(CategoryController.class).updateCategory(CATEGORY_ID, null)).toString();
    private static final String DELETE_CATEGORY = linkTo(methodOn(CategoryController.class).deleteCategory(CATEGORY_ID)).toString();


    @Test
    void getCategoryByIdTest() throws Exception {
        when(categoryService.getCategoryById(CATEGORY_ID)).thenReturn(CATEGORY_DTO);
        when(categoryAssembler.toModel(CATEGORY_DTO)).thenReturn(new CategoryModel(CATEGORY_DTO));
        mockMvc.perform(get(GET_BY_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(CATEGORY_ID))
                .andExpect(jsonPath("$.name").value(CATEGORY_NAME))
                .andExpect(jsonPath("$.parentId").value(CATEGORY_PARENT.getId()));
    }

    @Test
    void getCategoryById_NotFoundTest() throws Exception {
        when(categoryService.getCategoryById(CATEGORY_ID)).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get(GET_BY_ID))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void getSubcategoriesTest() throws Exception {
        when(categoryService.getSubcategories(CATEGORY_PARENT.getId())).thenReturn(List.of(CATEGORY_DTO));
        when(categoryAssembler.toModel(CATEGORY_DTO)).thenReturn(new CategoryModel(CATEGORY_DTO));
        mockMvc.perform(get(GET_SUBCATEGORIES))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(CATEGORY_ID))
                .andExpect(jsonPath("$.[0].name").value(CATEGORY_NAME))
                .andExpect(jsonPath("$.[0].parentId").value(CATEGORY_PARENT.getId()));
    }

    @Test
    void getRootCategoriesTest() throws Exception {
        when(categoryService.getRootCategories()).thenReturn(List.of(CATEGORY_DTO));
        when(categoryAssembler.toModel(CATEGORY_DTO)).thenReturn(new CategoryModel(CATEGORY_DTO));
        mockMvc.perform(get(GET_ROOT_CATEGORIES))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(CATEGORY_ID))
                .andExpect(jsonPath("$.[0].name").value(CATEGORY_NAME))
                .andExpect(jsonPath("$.[0].parentId").value(CATEGORY_PARENT.getId()));
    }

    @Test
    void addCategoryTest() throws Exception {
        when(categoryService.addCategory(any())).thenReturn(CATEGORY_DTO);
        when(categoryAssembler.toModel(CATEGORY_DTO)).thenReturn(new CategoryModel(CATEGORY_DTO));
        mockMvc.perform(post(ADD_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(CATEGORY_DTO))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.name").value(CATEGORY_NAME))
                .andExpect(jsonPath("$.parentId").value(CATEGORY_PARENT.getId()));
    }

    @Test
    void addCategory_NameIsNullTest() throws Exception {
        CategoryDto testDto = CategoryDto.builder().parentId(1).build();
        mockMvc.perform(post(ADD_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }


    @Test
    void addCategory_NameIsEmptyTest() throws Exception {
        CategoryDto testDto = CategoryDto.builder().name("").parentId(1).build();
        mockMvc.perform(post(ADD_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void addCategory_NameIsWhiteSpaceTest() throws Exception {
        CategoryDto testDto = CategoryDto.builder().name("   ").parentId(1).build();
        mockMvc.perform(post(ADD_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void addCategory_NegativeParentIdTest() throws Exception {
        CategoryDto testDto = CategoryDto.builder().name("Name").parentId(-1).build();
        mockMvc.perform(post(ADD_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void addCategory_ParentIdNotExistsTest() throws Exception {
        when(categoryService.addCategory(any())).thenThrow(CategoryNotFoundException.class);
        CategoryDto testDto = CategoryDto.builder().id(10).name("Name").parentId(11).build();
        mockMvc.perform(post(ADD_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }


    @Test
    void updateCategoryTest() throws Exception {
        when(categoryService.updateCategory(anyLong(), any())).thenReturn(CATEGORY_DTO);
        when(categoryAssembler.toModel(CATEGORY_DTO)).thenReturn(new CategoryModel(CATEGORY_DTO));
        mockMvc.perform(put(UPDATE_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(CATEGORY_DTO))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CATEGORY_ID))
                .andExpect(jsonPath("$.name").value(CATEGORY_NAME))
                .andExpect(jsonPath("$.parentId").value(CATEGORY_PARENT.getId()));
    }

    @Test
    void updateCategory_NotExistsTest() throws Exception {
        when(categoryService.updateCategory(anyLong(), any())).thenThrow(CategoryNotFoundException.class);
        mockMvc.perform(put(UPDATE_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(CATEGORY_DTO))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void deleteCategoryTest() throws Exception {
        mockMvc.perform(delete(DELETE_CATEGORY)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
        verify(categoryService).deleteCategory(CATEGORY_ID);
    }

    @Test
    void deleteCategory_NotFoundTest() throws Exception {
        doThrow(CategoryNotFoundException.class).when(categoryService).deleteCategory(anyLong());
        mockMvc.perform(delete(DELETE_CATEGORY))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

}