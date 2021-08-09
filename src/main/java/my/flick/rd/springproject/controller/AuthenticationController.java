package my.flick.rd.springproject.controller;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.api.AuthenticationApi;
import my.flick.rd.springproject.service.AuthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {
    private final AuthService authService;

    @Override
    public void authenticate(String email, String password) {
       authService.signIn(email,password);
    }
}
