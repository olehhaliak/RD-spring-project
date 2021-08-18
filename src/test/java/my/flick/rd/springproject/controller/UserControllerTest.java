package my.flick.rd.springproject.controller;

import my.flick.rd.springproject.Testconfig;
import my.flick.rd.springproject.controller.assembler.UserAssembler;
import my.flick.rd.springproject.controller.model.UserModel;
import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.exception.UserNotFoundException;
import my.flick.rd.springproject.service.UserService;
import my.flick.rd.springproject.test.utils.ObjectToJsonConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static my.flick.rd.springproject.test.utils.UserTestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import(Testconfig.class)

class UserControllerTest {
    private static final String GET_BY_ID = linkTo(methodOn(UserController.class).getUserById(USER_ID)).toString();
    private static final String GET_ALL_USERS = linkTo(methodOn(UserController.class).getAllUsers()).toString();
    private static final String CREATE_USER = linkTo(methodOn(UserController.class).createUser(null)).toString();
    private static final String UPDATE_USER = linkTo(methodOn(UserController.class).updateUser(USER_ID, null)).toString();
    private static final String DELETE_USER = linkTo(methodOn(UserController.class).deleteUser(USER_ID)).toString();
    private static final String BLOCK_USER = linkTo(methodOn(UserController.class).blockUser(USER_ID)).toString();
    private static final String UNBLOCK_USER = linkTo(methodOn(UserController.class).unblockUser(USER_ID)).toString();


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectToJsonConverter jsonConverter;

    @MockBean
    UserAssembler userAssembler;

    @MockBean
    UserService userService;

    @Autowired
    UserController userController;

    @Test
    void getUserByIdTest() throws Exception {
        when(userService.getUserById(USER_ID)).thenReturn(testUserDto());
        when(userAssembler.toModel(testUserDto())).thenReturn(new UserModel(testUserDto()));
        mockMvc.perform(get(GET_BY_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.role").value(USER_ROLE.toString()))
                .andExpect(jsonPath("$.blocked").value(USER_IS_BLOCKED));
    }

    @Test
    void getUserById_NotFoundTest() throws Exception {
        when(userService.getUserById(USER_ID)).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get(GET_BY_ID))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void getAllUsersTest() throws Exception{
        when(userService.getAllUsers()).thenReturn(List.of(testUserDto()));
        when(userAssembler.toModel(testUserDto())).thenReturn(new UserModel(testUserDto()));
        mockMvc.perform(get(GET_ALL_USERS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(USER_ID))
                .andExpect(jsonPath("$.[0].email").value(USER_EMAIL))
                .andExpect(jsonPath("$.[0].role").value(USER_ROLE.toString()))
                .andExpect(jsonPath("$.[0].blocked").value(USER_IS_BLOCKED));
    }

    @Test
    void createUserTest() throws Exception {
        when(userService.addUser(any())).thenReturn(testUserDto());
        when(userAssembler.toModel(testUserDto())).thenReturn(new UserModel(testUserDto()));
        mockMvc.perform(post(CREATE_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testUserDto()))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.role").value(USER_ROLE.toString()))
                .andExpect(jsonPath("$.blocked").value(USER_IS_BLOCKED));
    }


    @Test
    void createUser_WhitespaceEmailTest() throws Exception {
        UserDto testUserDto = testUserDto();
        testUserDto.setEmail("    ");

        mockMvc.perform(post(CREATE_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testUserDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void createUser_WhitespacePasswordTest() throws Exception {
        UserDto testUserDto = testUserDto();
        testUserDto.setPassword("    ");

        mockMvc.perform(post(CREATE_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testUserDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }


    @Test
    void createUser_WhitespaceRepeatPasswordTest() throws Exception {
        UserDto testUserDto = testUserDto();
        testUserDto.setPasswordRepeat("    ");

        mockMvc.perform(post(CREATE_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testUserDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void createUser_NullRoleTest() throws Exception {
        UserDto testUserDto = testUserDto();
        testUserDto.setRole(null);

        mockMvc.perform(post(CREATE_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testUserDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void updateUserTest() throws Exception {
        UserDto testUserDto = UserDto.builder().id(USER_ID).email(USER_EMAIL).build();
        when(userService.updateUser(anyLong(), any())).thenReturn(testUserDto());
        when(userAssembler.toModel(testUserDto())).thenReturn(new UserModel(testUserDto()));
        mockMvc.perform(patch(UPDATE_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testUserDto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.role").value(USER_ROLE.toString()))
                .andExpect(jsonPath("$.blocked").value(USER_IS_BLOCKED));
    }

    @Test
    void updateUser_PasswordFieldPresentTest() throws Exception {
        UserDto testUserDto = UserDto.builder().id(USER_ID).email(USER_EMAIL).password(USER_PASSWORD).build();
        mockMvc.perform(patch(UPDATE_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testUserDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void updateUser_RepeatPasswordFieldPresentTest() throws Exception {
        UserDto testUserDto = UserDto.builder().id(USER_ID).email(USER_EMAIL).passwordRepeat(USER_PASSWORD).build();
        mockMvc.perform(patch(UPDATE_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.asJsonString(testUserDto))
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void deleteUserTest() throws Exception {
        doNothing().when(userService).deleteUser(USER_ID);
        mockMvc.perform(delete(DELETE_USER))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
        verify(userService).deleteUser(USER_ID);
    }

    @Test
    void deleteUser_NotFoundTest() throws Exception {
        doThrow(UserNotFoundException.class).when(userService).deleteUser(USER_ID);
        mockMvc.perform(delete(DELETE_USER))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void blockUserTest() throws Exception{
        doNothing().when(userService).blockUser(USER_ID);
        mockMvc.perform(patch(BLOCK_USER))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userService).blockUser(USER_ID);
    }

    @Test
    void blockUser_NotFoundTest() throws Exception{
        doThrow(UserNotFoundException.class).when(userService).blockUser(USER_ID);
        mockMvc.perform(patch(BLOCK_USER))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void unblockUserTest() throws Exception{
        doNothing().when(userService).unblockUser(USER_ID);
        mockMvc.perform(patch(UNBLOCK_USER))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userService).unblockUser(USER_ID);
    }

    @Test
    void unblockUser_NotFoundTest() throws Exception{
        doThrow(UserNotFoundException.class).when(userService).unblockUser(USER_ID);
        mockMvc.perform(patch(UNBLOCK_USER))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

}