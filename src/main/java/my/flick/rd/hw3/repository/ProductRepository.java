package my.flick.rd.hw3.repository;

import my.flick.rd.hw3.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface ProductRepository extends CrudRepository<Product,Long> {
}
