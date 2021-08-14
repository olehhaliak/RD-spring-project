package my.flick.rd.springproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class OrderItemDto {

    @Positive
    private long productId;
    @Positive
    private int quantity;
}
