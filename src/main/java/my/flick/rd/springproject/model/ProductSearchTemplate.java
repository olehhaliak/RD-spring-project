package my.flick.rd.springproject.model;


import lombok.Builder;
import lombok.Getter;
import my.flick.rd.springproject.model.enums.SortOption;

import java.math.BigDecimal;

@Builder
@Getter
public class ProductSearchTemplate {
    private SortOption sortBy;
    private boolean descendingOrder = false;
    private long inCategory;
    private BigDecimal withMinPrice;
    private BigDecimal withMaxPrice;


}
