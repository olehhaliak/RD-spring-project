package my.flick.rd.springproject.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import my.flick.rd.springproject.model.enums.SortOption;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class ProductSearchTemplate {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private SortOption sortBy;
    private boolean descendingOrder = false;
    private Long inCategory;
    private BigDecimal withMinPrice;
    private BigDecimal withMaxPrice;
    private int page = DEFAULT_PAGE;
    private int pageSize =DEFAULT_PAGE_SIZE;

    public ProductSearchTemplate() {
          page = DEFAULT_PAGE;
          pageSize =DEFAULT_PAGE_SIZE;
    }
}
