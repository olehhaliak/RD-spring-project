package my.flick.rd.springproject.service.impl;

import my.flick.rd.springproject.exception.AdminPrivilegesRequiredException;
import my.flick.rd.springproject.exception.UserIsNotCustomerException;
import my.flick.rd.springproject.exception.UserNotAuthentificatedException;
import my.flick.rd.springproject.model.SecurityContext;
import my.flick.rd.springproject.model.User;
import my.flick.rd.springproject.model.enums.Role;
import my.flick.rd.springproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

import static my.flick.rd.springproject.test.utils.UserTestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "security.admin.checkPrivileges = true"
})
@ContextConfiguration(classes = AuthServiceImpl.class)
class AuthServiceImplTest {
    @MockBean
    SecurityContext securityContext;
    @MockBean
    UserService userService;
    @Autowired
    AuthServiceImpl authService;

    @Test
    void checkAdminPrivilegesTest() {
        User testUser = testUser();
        testUser.setRole(Role.ADMIN);
        when(securityContext.getSessionAssociatedUser()).thenReturn(testUser);
        authService.checkAdminPrivileges();
    }

    @Test
    void checkAdminPrivileges_NotAuthenticatedTest() {
        when(securityContext.getSessionAssociatedUser()).thenThrow(UserNotAuthentificatedException.class);
        assertThrows(UserNotAuthentificatedException.class, () -> authService.checkAdminPrivileges());
    }

    @Test
    void checkAdminPrivileges_UserIsNotAdminTest() {
        User testUser = testUser();
        testUser.setRole(Role.CUSTOMER);
        when(securityContext.getSessionAssociatedUser()).thenReturn(testUser);
        assertThrows(AdminPrivilegesRequiredException.class, () -> authService.checkAdminPrivileges());
    }

    @Test
    void checkAdminPrivileges_IsTurnedOffTest() throws NoSuchFieldException, IllegalAccessException {
        Field checkRequired = authService.getClass().getDeclaredField("adminCheckRequired");
        checkRequired.setAccessible(true);
        checkRequired.setBoolean(authService, false);
        authService.checkAdminPrivileges();
        checkRequired.setBoolean(authService, true);
        checkRequired.setAccessible(false);
    }

    @Test
    void sighInTest() {
        when(userService.getAuthenticatedUser(USER_EMAIL, USER_PASSWORD)).thenReturn(testUser());
        authService.signIn(USER_EMAIL, USER_PASSWORD);
        verify(securityContext).associateUserWithSession(testUser());
    }

    @Test
    void getCustomerTest() {
        User testUser = testUser();
        testUser.setRole(Role.CUSTOMER);
        when(securityContext.getSessionAssociatedUser()).thenReturn(testUser);
        assertThat(authService.getCustomer(),is(equalTo(testUser)));
    }

    @Test
    void getCustomer_NotAuthenticatedTest() {
        when(securityContext.getSessionAssociatedUser()).thenThrow(UserNotAuthentificatedException.class);
        assertThrows(UserNotAuthentificatedException.class,()->authService.getCustomer());
    }

    @Test
    void getCustomer_UserIsNotCustomer() {
        User testUser = testUser();
        testUser.setRole(Role.ADMIN);
        when(securityContext.getSessionAssociatedUser()).thenReturn(testUser);
        assertThrows(UserIsNotCustomerException.class,()->authService.getCustomer());
    }
}