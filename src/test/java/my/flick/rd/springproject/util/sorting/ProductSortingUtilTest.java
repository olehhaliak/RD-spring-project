package my.flick.rd.springproject.util.sorting;

import my.flick.rd.springproject.model.Product;
import my.flick.rd.springproject.model.enums.SortOption;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;

class ProductSortingUtilTest {
    Product testProduct1 = Product.builder().name("a").price(new BigDecimal(200)).creationTime(LocalDateTime.of(2020, 1, 1, 1, 1)).build();
    Product testProduct2 = Product.builder().name("b").price(new BigDecimal(300)).creationTime(LocalDateTime.of(2015, 1, 1, 1, 1)).build();
    Product testProduct3 = Product.builder().name("c").price(new BigDecimal(100)).creationTime(LocalDateTime.of(2010, 1, 1, 1, 1)).build();

    ProductSortingUtil sortingUtil = new ProductSortingUtil();
    private List<Product> testList() {
        return  List.of(testProduct1,testProduct2,testProduct3);
    }

    @Test
    void sortByName_NaturalOrder() {
       List<Product> sortedList = sortingUtil.sortProducts(testList(), SortOption.NAME,false);
       assertThat(sortedList,contains(testProduct1,testProduct2,testProduct3));
    }

    @Test
    void sortByName_DescendingOrder() {
        List<Product> sortedList = sortingUtil.sortProducts(testList(), SortOption.NAME,true);
        assertThat(sortedList,contains(testProduct3,testProduct2,testProduct1));
    }

    @Test
    void sortByPrice_NaturelOrder() {
        List<Product> sortedList = sortingUtil.sortProducts(testList(), SortOption.PRICE,false);
        assertThat(sortedList,contains(testProduct3,testProduct1,testProduct2));
    }

    @Test
    void sortByPrice_DescendingOrder() {
        List<Product> sortedList = sortingUtil.sortProducts(testList(), SortOption.PRICE,true);
        assertThat(sortedList,contains(testProduct2,testProduct1,testProduct3));
    }

    @Test
    void sortByPublication_NaturelOrder() {
        List<Product> sortedList = sortingUtil.sortProducts(testList(), SortOption.PUBLICATION_TIME,false);
        assertThat(sortedList,contains(testProduct3,testProduct2,testProduct1));
    }

    @Test
    void sortByPublication_DescendingOrder() {
        List<Product> sortedList = sortingUtil.sortProducts(testList(), SortOption.PUBLICATION_TIME,true);
        assertThat(sortedList,contains(testProduct1,testProduct2,testProduct3));
    }
}