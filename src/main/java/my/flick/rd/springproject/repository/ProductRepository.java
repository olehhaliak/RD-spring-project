package my.flick.rd.springproject.repository;

import my.flick.rd.springproject.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface ProductRepository extends CrudRepository<Product,Long> {
}
