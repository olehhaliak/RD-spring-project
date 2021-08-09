package my.flick.rd.springproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import my.flick.rd.springproject.model.OrderItem;
import my.flick.rd.springproject.model.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    long id;
    Status status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long customerId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<OrderItem> items;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime creationTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime updateTime;
}
