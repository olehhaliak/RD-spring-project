package my.flick.rd.hw3.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionLoggingAspect {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void anyExceptionHandler(){}

    @Before("anyExceptionHandler()")

    public void log(JoinPoint joinPoint) {
        Exception exception = (Exception) joinPoint.getArgs()[0];
      log.error(exception.getMessage(),exception);
    }
}
