package my.flick.rd.springproject.util.sorting;

import my.flick.rd.springproject.model.Product;
import my.flick.rd.springproject.model.enums.SortOption;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductSortingUtil {

    public List<Product> sortProducts(List<Product> products, SortOption sortBy, boolean descendingOrder) {
        var stream = products.stream();
        if (sortBy.equals(SortOption.NAME)) {
            stream = stream.sorted(Comparator.comparing(Product::getName));
        }
        if (sortBy.equals(SortOption.PRICE)) {
            stream = stream.sorted(Comparator.comparing(Product::getPrice));
        }
        if (sortBy.equals(SortOption.PUBLICATION_TIME)) {
            stream = stream.sorted(Comparator.comparing(Product::getCreationTime));
        }
        products = stream.collect(Collectors.toList());
        if (descendingOrder) {
            Collections.reverse(products);
        }
        return products;
    }
}
