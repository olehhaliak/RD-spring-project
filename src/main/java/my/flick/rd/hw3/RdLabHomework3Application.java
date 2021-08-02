package my.flick.rd.hw3;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class RdLabHomework3Application {

    public static void main(String[] args) {
        SpringApplication.run(RdLabHomework3Application.class, args);
    }

    @Bean("propertyUtils")
    PropertyUtilsBean getPropertyUtils(){
        return new PropertyUtilsBean();
    }
}
