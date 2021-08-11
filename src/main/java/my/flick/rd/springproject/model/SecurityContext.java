package my.flick.rd.springproject.model;

import lombok.Getter;
import lombok.Setter;
import my.flick.rd.springproject.exception.UserNotAuthentificatedException;
import my.flick.rd.springproject.service.AuthService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Session-scoped container for currently authenticated user
 * used to perform security checks
 *
 * @see AuthService
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Setter
public class SecurityContext {
    private User user;

    public User getCurrentUser() {
        if (user == null) {
            throw new UserNotAuthentificatedException("accessing this resource requires authentication");
        }
        return user;
    }
}
