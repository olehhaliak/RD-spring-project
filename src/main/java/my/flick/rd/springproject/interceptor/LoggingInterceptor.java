package my.flick.rd.springproject.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoggingInterceptor extends WebRequestHandlerInterceptorAdapter {
    public LoggingInterceptor(WebRequestInterceptor requestInterceptor) {
        super(requestInterceptor);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getSession().getId() + " : " + request.getMethod() + " " + request.getRequestURI() + "");
        return super.preHandle(request, response, handler);
    }
}
