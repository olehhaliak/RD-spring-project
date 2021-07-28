package my.flick.rd.hw3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private String name;
    private BigDecimal price;
    private String description;
    private int quantity;
}
