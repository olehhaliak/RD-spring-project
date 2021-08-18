package my.flick.rd.springproject;

import my.flick.rd.springproject.api.config.SwaggerConfig;
import my.flick.rd.springproject.config.WebMvcConfig;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAspectJAutoProxy
@Import({SwaggerConfig.class, WebMvcConfig.class})
public class RdSpringProject {

    public static void main(String[] args) {
        SpringApplication.run(RdSpringProject.class, args);
    }

    @Bean("propertyUtils")
    PropertyUtilsBean getPropertyUtils(){
        return new PropertyUtilsBean();
    }
}
