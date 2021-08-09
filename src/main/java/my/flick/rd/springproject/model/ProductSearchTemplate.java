package my.flick.rd.springproject.model;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import my.flick.rd.springproject.model.enums.SortOption;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class ProductSearchTemplate {
    private SortOption sortBy;
    private boolean descendingOrder = false;
    private Long inCategory;
    private BigDecimal withMinPrice;
    private BigDecimal withMaxPrice;


}
