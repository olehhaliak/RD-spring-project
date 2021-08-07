package my.flick.rd.springproject.controller.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import my.flick.rd.springproject.dto.CategoryDto;
import org.springframework.hateoas.RepresentationModel;


@AllArgsConstructor
public class CategoryModel extends RepresentationModel<CategoryModel> {
    @JsonUnwrapped
    CategoryDto categoryDto;
}
