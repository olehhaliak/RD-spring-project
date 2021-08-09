package my.flick.rd.springproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.flick.rd.springproject.model.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    long id;
    Status status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long customerId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<OrderItemDto> items;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime creationTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime updateTime;
}
