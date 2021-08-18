package my.flick.rd.springproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.flick.rd.springproject.model.Category;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @NotBlank
    private String name;
    @PositiveOrZero
    private long parentId;
}
