package my.flick.rd.springproject.repository;

import my.flick.rd.springproject.model.Product;
import my.flick.rd.springproject.model.ProductSearchTemplate;
import my.flick.rd.springproject.model.enums.SortOption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT p from Product p where( :category is null or p.category.id = :category)" +
            "and (:minPrice is  null or p.price >= :minPrice)" +
            "and (:maxPrice is  null or p.price <= :maxPrice)"
    )
    List<Product> findBySearchParams(long category,
                                     BigDecimal minPrice,
                                     BigDecimal maxPrice//,
//                                     SortOption sortBy,
//                                     boolean descendingOrder
    );
}
