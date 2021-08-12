package my.flick.rd.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.flick.rd.springproject.exception.AdminPrivilegesRequiredException;
import my.flick.rd.springproject.exception.UserIsNotCustomerException;
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
    private final UserService userService;
    @Value("${security.admin.privileges.check:true}")
    private boolean adminCheckRequired;

    @Override
    public void checkAdminPrivileges() {
        if (adminCheckRequired && securityContext.getSessionAssociatedUser().getRole() != Role.ADMIN) {
            throw new AdminPrivilegesRequiredException("accessing this resource requires admin privileges");
        }
    }

    @Override
    public void signIn(String email, String password) {
        securityContext.associateUserWithSession(userService.getAuthenticatedUser(email, password));
    }

    @Override
    public User getCustomer() {
        if (securityContext.getSessionAssociatedUser().getRole().equals(Role.CUSTOMER)) {
            return securityContext.getSessionAssociatedUser();
        }
        throw new UserIsNotCustomerException("current user is not a customer");
    }



    @PostConstruct
    private void postConstruct() {
        log.info("admin privileges check is turned " + (adminCheckRequired ? "ON" : "OFF"));
    }

}
