package my.flick.rd.springproject.service;

import my.flick.rd.springproject.model.User;
import org.springframework.stereotype.Service;

public interface AuthService {

    void checkAdminPrivileges();

    void signIn(String email,String password);

    User getCustomer();
}
