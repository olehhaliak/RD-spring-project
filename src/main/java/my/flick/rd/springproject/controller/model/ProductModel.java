package my.flick.rd.springproject.controller.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import my.flick.rd.springproject.dto.ProductDto;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
public class ProductModel extends RepresentationModel<ProductModel> {
    @JsonUnwrapped
    ProductDto productDto;
}
