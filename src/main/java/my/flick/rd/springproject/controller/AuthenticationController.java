package my.flick.rd.springproject.controller;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.api.AuthenticationApi;
import my.flick.rd.springproject.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {
    private final SecurityService securityService;

    @Override
    public void authenticate(String email, String password) {
       securityService.signIn(email,password);
    }
}
