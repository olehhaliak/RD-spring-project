package my.flick.rd.springproject.model;

import lombok.Getter;
import lombok.Setter;
import my.flick.rd.springproject.service.AuthService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Session-scoped wrapper for user
 * used to perform security checks
 * @see AuthService
 */
@Component
@Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class SecurityContext {
    private User user;

}
