package my.flick.rd.springproject.repository;

import my.flick.rd.springproject.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p from Product p where( :category is null or p.category.id = :category)" +
            "and (:minPrice is  null or p.price >= :minPrice)" +
            "and (:maxPrice is  null or p.price <= :maxPrice)"
    )
    Page<Product> findBySearchParams(Long category,
                                     BigDecimal minPrice,
                                     BigDecimal maxPrice,//,
//                                     SortOption sortBy,
//                                     boolean descendingOrder
                                     Pageable pageable);
}
