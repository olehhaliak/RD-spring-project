package my.flick.rd.springproject;

import my.flick.rd.springproject.api.config.SwaggerConfig;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAspectJAutoProxy
@Import(SwaggerConfig.class)
public class RdLabHomework3Application {

    public static void main(String[] args) {
        SpringApplication.run(RdLabHomework3Application.class, args);
    }

    @Bean("propertyUtils")
    PropertyUtilsBean getPropertyUtils(){
        return new PropertyUtilsBean();
    }
}
