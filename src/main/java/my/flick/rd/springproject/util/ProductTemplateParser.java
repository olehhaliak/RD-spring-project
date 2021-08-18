package my.flick.rd.springproject.util;

import my.flick.rd.springproject.model.ProductSearchTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


@Component
public class ProductTemplateParser {


    public Pageable parseToPageable(ProductSearchTemplate searchTemplate){
        Sort sort;
        if (searchTemplate.getSortBy() != null) {
            sort = Sort.by(
                    searchTemplate.isDescendingOrder() ? Sort.Direction.DESC : Sort.Direction.ASC,
                    searchTemplate.getSortBy().getValue()
            );
        } else {
            sort = Sort.by(Sort.Direction.ASC);
        }
        return  PageRequest.of(searchTemplate.getPage(), searchTemplate.getPageSize(), sort);
    }
}
