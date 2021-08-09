package my.flick.rd.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.exception.AdminPrivilegesRequiredException;
import my.flick.rd.springproject.exception.UserIsNotCustomerException;
import my.flick.rd.springproject.exception.UserNotAuthentificatedException;
import my.flick.rd.springproject.model.SecurityContext;
import my.flick.rd.springproject.model.User;
import my.flick.rd.springproject.model.enums.Role;
import my.flick.rd.springproject.service.AuthService;
import my.flick.rd.springproject.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final SecurityContext securityContext;
    private final UserService userService;

    @Override
    public void checkAdminPrivileges() {
        if(currentUser()==null||currentUser().getRole()!= Role.ADMIN){
            throw new AdminPrivilegesRequiredException("accessing this resource requires admin privileges");
        }
    }

    @Override
    public void signIn(String email, String password) {
        securityContext.setUser(userService.getAuthenticatedUser(email,password));
    }

    @Override
    public User getCustomer() {
        if(currentUser().getRole().equals(Role.CUSTOMER)){
            return currentUser();
        }
       throw new UserIsNotCustomerException("current user is not a customer");
    }

    private User currentUser(){
        if(securityContext.getUser()==null){
            throw new UserNotAuthentificatedException("accessing this resource requires authentication");
        }
        return securityContext.getUser();
    }

}
