package my.flick.rd.springproject.service;

import org.springframework.stereotype.Service;

public interface SecurityService {

    void checkAdminPrivileges();

    void signIn(String email,String password);//todo:change pass String --> char[]
}
