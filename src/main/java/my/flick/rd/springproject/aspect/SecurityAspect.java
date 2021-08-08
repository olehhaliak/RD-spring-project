package my.flick.rd.springproject.aspect;

import lombok.RequiredArgsConstructor;
import my.flick.rd.springproject.service.SecurityService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAspect {
    private final SecurityService securityService;

    @Pointcut("@annotation(my.flick.rd.springproject.annotation.RequireAdminPrivileges)")
    public void requireAdminPrivileges() {
    }

    @Before("requireAdminPrivileges()")
    public void checkAdminPrivileges() {
        securityService.checkAdminPrivileges();
    }
}
