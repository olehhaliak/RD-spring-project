package my.flick.rd.springproject.controller;

import my.flick.rd.springproject.exception.InvalidCredentialsException;
import my.flick.rd.springproject.exception.UserIsBlockedException;
import my.flick.rd.springproject.exception.UserNotFoundException;
import my.flick.rd.springproject.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static my.flick.rd.springproject.test.utils.UserTestData.USER_EMAIL;
import static my.flick.rd.springproject.test.utils.UserTestData.USER_PASSWORD;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {
    private static String AUTHENTICATE = "/api/v1/auth";
    @MockBean
    AuthService authService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void authenticateTest() throws Exception {
        mockMvc.perform(post(AUTHENTICATE)
                .header("email", USER_EMAIL)
                .header("password", USER_PASSWORD)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void authenticate_InvalidCredentialsTestTest() throws Exception {
       doThrow(InvalidCredentialsException.class).when(authService).signIn(USER_EMAIL,USER_PASSWORD);
        mockMvc.perform(post(AUTHENTICATE)
                .header("email", USER_EMAIL)
                .header("password", USER_PASSWORD)
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    void authenticate_UserNotFoundTest() throws Exception {
        doThrow(UserNotFoundException.class).when(authService).signIn(USER_EMAIL,USER_PASSWORD);
        mockMvc.perform(post(AUTHENTICATE)
                .header("email", USER_EMAIL)
                .header("password", USER_PASSWORD)
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void authenticate_UserIsBlockedTest() throws Exception {
        doThrow(UserIsBlockedException.class).when(authService).signIn(USER_EMAIL,USER_PASSWORD);
        mockMvc.perform(post(AUTHENTICATE)
                .header("email", USER_EMAIL)
                .header("password", USER_PASSWORD)
        )
                .andDo(print())
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }
}