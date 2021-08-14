package my.flick.rd.springproject;

import lombok.Builder;
import my.flick.rd.springproject.test.utils.ObjectToJsonConverter;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@TestConfiguration
public class Testconfig {

    @Bean
    ObjectToJsonConverter objectToJsonConverter(){
        return new ObjectToJsonConverter();
    }
//    @Bean("propertyUtils")
//    PropertyUtilsBean getPropertyUtils() {
//        return new PropertyUtilsBean();
//    }
}
