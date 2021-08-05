package my.flick.rd.hw3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @NotBlank(message = "name should not be blank")
    private String name;

    @Positive(message = "price should be a positive number")
    private BigDecimal price;

    private String description;
    @PositiveOrZero(message = "quantity should be equal or greater than 0")
    private int quantity;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    @Null(message = "update time should be absent in request")
    private LocalDateTime creationTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Null(message = "update time should be absent in request")
    private LocalDateTime updateTime;
}
