package my.flick.rd.hw3.controller.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import my.flick.rd.hw3.dto.ProductDto;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
public class ProductModel extends RepresentationModel<ProductModel> {
    @JsonUnwrapped
    ProductDto productDto;
}
