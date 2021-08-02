package my.flick.rd.hw3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private String name;
    private BigDecimal price;
    private String description;
    private int quantity;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updateTime;
}
