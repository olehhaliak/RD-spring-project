package my.flick.rd.springproject.api;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(tags = "Authentication")
@RequestMapping("/api/v1/auth")
public interface AuthenticationApi {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void authenticate(@RequestHeader String email,@RequestHeader String password);
}
