package my.flick.rd.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.flick.rd.springproject.exception.AdminPrivilegesRequiredException;
import my.flick.rd.springproject.exception.UserIsNotCustomerException;
import my.flick.rd.springproject.exception.UserNotAuthentificatedException;
import my.flick.rd.springproject.model.SecurityContext;
import my.flick.rd.springproject.model.User;
import my.flick.rd.springproject.model.enums.Role;
import my.flick.rd.springproject.service.AuthService;
import my.flick.rd.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final SecurityContext securityContext;
    private UserService userService;
    @Value("${security.admin.checkPrivileges:true}")
    private  boolean adminCheckRequired;

    @Override
    public void checkAdminPrivileges() {
        if(!adminCheckRequired){
            return;
        }
        if (currentUser() == null || currentUser().getRole() != Role.ADMIN) {
            throw new AdminPrivilegesRequiredException("accessing this resource requires admin privileges");
        }
    }

    @Override
    public void signIn(String email, String password) {
        securityContext.setUser(userService.getAuthenticatedUser(email, password));
    }

    @Override
    public User getCustomer() {
        if (currentUser().getRole().equals(Role.CUSTOMER)) {
            return currentUser();
        }
        throw new UserIsNotCustomerException("current user is not a customer");
    }

    private User currentUser() {
        if (securityContext.getUser() == null) {
            throw new UserNotAuthentificatedException("accessing this resource requires authentication");
        }
        return securityContext.getUser();
    }

    @PostConstruct
    private void postConstruct(){
        log.info("admin privileges check is turned "+(adminCheckRequired?"ON":"OFF"));
    }

}
