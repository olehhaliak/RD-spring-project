package my.flick.rd.springproject.service.impl;

import my.flick.rd.springproject.RdSpringProject;
import my.flick.rd.springproject.Testconfig;
import my.flick.rd.springproject.model.SecurityContext;
import my.flick.rd.springproject.service.AuthService;
import my.flick.rd.springproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "security.admin.checkPrivileges = false"
})
@ContextConfiguration(classes = AuthServiceImpl.class)
class AuthServiceImplTest {
   @MockBean
    SecurityContext context;
   @MockBean
    UserService userService;
   @Autowired
    AuthServiceImpl authService;

    @Test
    void name() {
        System.out.println("test");
    }
}